package com.guitarstore.guitar_store_api.controller;

import com.guitarstore.guitar_store_api.dto.GuitarDto;
import com.guitarstore.guitar_store_api.dto.GuitarRequest;
import com.guitarstore.guitar_store_api.service.GuitarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/guitars")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GuitarController {

    private final GuitarService guitarService;

    @GetMapping
    public ResponseEntity<Page<GuitarDto>> list(
            @RequestParam(required = false) String     search,
            @RequestParam(required = false) Integer    brand,
            @RequestParam(required = false) Integer    category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @PageableDefault(size = 20) Pageable       pageable
    ) {
        return ResponseEntity.ok(
                guitarService.findAll(search, brand, category, minPrice, maxPrice, pageable)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuitarDto> get(@PathVariable UUID id) {
        return ResponseEntity.ok(guitarService.findById(id));
    }

    @PostMapping
    public ResponseEntity<GuitarDto> create(@Valid @RequestBody GuitarRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(guitarService.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GuitarDto> update(
            @PathVariable UUID id,
            @Valid @RequestBody GuitarRequest req
    ) {
        return ResponseEntity.ok(guitarService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        guitarService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
