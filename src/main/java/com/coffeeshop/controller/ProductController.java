package com.coffeeshop.controller;

import com.coffeeshop.dto.ProductDTO;
import com.coffeeshop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Product management APIs")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Returns paginated and sorted list of products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of products",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class)))
    })
    public ResponseEntity<Page<ProductDTO>> getAllProducts(
            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Returns a single product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductDTO> getProductById(
            @Parameter(description = "ID of the product to retrieve") @PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{categoryName}")
    @Operation(summary = "Get products by category", description = "Returns paginated list of products filtered by category name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found for the given category",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class)))
    })
    public ResponseEntity<Page<ProductDTO>> getProductsByCategory(
            @Parameter(description = "Category name to filter products by") @PathVariable String categoryName,
            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = "price") Pageable pageable) {
        return ResponseEntity.ok(productService.getProductsByCategoryName(categoryName, pageable));
    }

    @GetMapping("/search")
    @Operation(summary = "Search products by name and price range",
            description = "Find products by part of the name and a price range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Matching products found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class)))
    })
    public ResponseEntity<List<ProductDTO>> searchProducts(
            @Parameter(description = "Part of the product name to search for") @RequestParam String namePart,
            @Parameter(description = "Minimum price") @RequestParam BigDecimal minPrice,
            @Parameter(description = "Maximum price") @RequestParam BigDecimal maxPrice) {
        List<ProductDTO> products = productService.searchByNameAndPriceRange(namePart, minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }
}
