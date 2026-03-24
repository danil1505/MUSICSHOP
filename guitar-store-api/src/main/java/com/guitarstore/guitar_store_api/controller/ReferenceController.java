package com.guitarstore.guitar_store_api.controller;

import com.guitarstore.guitar_store_api.model.Brand;
import com.guitarstore.guitar_store_api.model.Category;
import com.guitarstore.guitar_store_api.repository.BrandRepository;
import com.guitarstore.guitar_store_api.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReferenceController {

    private final BrandRepository    brandRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping("/brands")
    public ResponseEntity<List<Brand>> brands() {
        return ResponseEntity.ok(brandRepository.findAll());
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> categories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }
}
