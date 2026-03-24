package com.guitarstore.guitar_store_api.dto;

import com.guitarstore.guitar_store_api.model.Guitar;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class GuitarDto {

    private UUID           id;
    private String         name;
    private Integer        brandId;
    private String         brandName;
    private Integer        categoryId;
    private String         categoryName;
    private BigDecimal     price;
    private String         description;
    private String         imageUrl;
    private Boolean        inStock;
    private OffsetDateTime createdAt;

    public static GuitarDto from(Guitar g) {
        return GuitarDto.builder()
                .id(g.getId())
                .name(g.getName())
                .brandId(g.getBrand().getId())
                .brandName(g.getBrand().getName())
                .categoryId(g.getCategory().getId())
                .categoryName(g.getCategory().getName())
                .price(g.getPrice())
                .description(g.getDescription())
                .imageUrl(g.getImageUrl())
                .inStock(g.getInStock())
                .createdAt(g.getCreatedAt())
                .build();
    }
}
