package com.guitarstore.guitar_store_api.service;

import com.guitarstore.guitar_store_api.dto.GuitarDto;
import com.guitarstore.guitar_store_api.dto.GuitarRequest;
import com.guitarstore.guitar_store_api.model.Brand;
import com.guitarstore.guitar_store_api.model.Category;
import com.guitarstore.guitar_store_api.model.Guitar;
import com.guitarstore.guitar_store_api.repository.BrandRepository;
import com.guitarstore.guitar_store_api.repository.CategoryRepository;
import com.guitarstore.guitar_store_api.repository.GuitarRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GuitarService {

    private final GuitarRepository   guitarRepository;
    private final BrandRepository    brandRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<GuitarDto> findAll(
            String search,
            Integer brandId,
            Integer categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable
    ) {
        String searchParam = (search == null || search.isBlank()) ? null : search.trim();

        Page<Guitar> page = guitarRepository.findWithFilters(
                searchParam, brandId, categoryId, minPrice, maxPrice, pageable
        );

        List<GuitarDto> dtos = page.getContent()
                .stream()
                .map(GuitarDto::from)
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }

    @Transactional(readOnly = true)
    public GuitarDto findById(UUID id) {
        Guitar guitar = guitarRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Гитара не найдена: " + id));
        return GuitarDto.from(guitar);
    }

    @Transactional
    public GuitarDto create(GuitarRequest req) {
        Brand    brand    = getBrand(req.getBrandId());
        Category category = getCategory(req.getCategoryId());

        Guitar guitar = Guitar.builder()
                .name(req.getName())
                .brand(brand)
                .category(category)
                .price(req.getPrice())
                .description(req.getDescription())
                .imageUrl(req.getImageUrl())
                .inStock(req.getInStock() != null ? req.getInStock() : true)
                .build();

        return GuitarDto.from(guitarRepository.save(guitar));
    }

    @Transactional
    public GuitarDto update(UUID id, GuitarRequest req) {
        Guitar   guitar   = guitarRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Гитара не найдена: " + id));
        Brand    brand    = getBrand(req.getBrandId());
        Category category = getCategory(req.getCategoryId());

        guitar.setName(req.getName());
        guitar.setBrand(brand);
        guitar.setCategory(category);
        guitar.setPrice(req.getPrice());
        guitar.setDescription(req.getDescription());
        guitar.setImageUrl(req.getImageUrl());
        if (req.getInStock() != null) guitar.setInStock(req.getInStock());

        return GuitarDto.from(guitarRepository.save(guitar));
    }

    @Transactional
    public void delete(UUID id) {
        if (!guitarRepository.existsById(id))
            throw new EntityNotFoundException("Гитара не найдена: " + id);
        guitarRepository.deleteById(id);
    }

    private Brand getBrand(Integer id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Бренд не найден: " + id));
    }

    private Category getCategory(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Категория не найдена: " + id));
    }
}
