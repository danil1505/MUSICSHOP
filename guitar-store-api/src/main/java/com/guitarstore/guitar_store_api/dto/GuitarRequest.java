package com.guitarstore.guitar_store_api.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class GuitarRequest {

    @NotBlank(message = "Название не может быть пустым")
    @Size(max = 255)
    private String name;

    @NotNull(message = "Укажите бренд")
    private Integer brandId;

    @NotNull(message = "Укажите категорию")
    private Integer categoryId;

    @NotNull(message = "Укажите цену")
    @DecimalMin(value = "0.01", message = "Цена должна быть больше 0")
    private BigDecimal price;

    private String description;

    @Size(max = 500)
    private String imageUrl;

    private Boolean inStock = true;
}
