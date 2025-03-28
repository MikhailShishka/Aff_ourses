package com.coffeeshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO for Product entity")
public class ProductDTO {

    @Schema(description = "Unique identifier of the product", example = "1")
    private Long id;

    @Schema(description = "Name of the product", example = "Cappuccino")
    private String name;

    @Schema(description = "Description of the product", example = "A delicious hot coffee with milk foam")
    private String description;

    @Schema(description = "Price of the product", example = "3.50")
    private BigDecimal price;

    @Schema(description = "URL of the product image", example = "https://example.com/images/cappuccino.jpg")
    private String imageUrl;

    @Schema(description = "Product availability status", example = "true")
    private boolean available;
}
