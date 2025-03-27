package com.coffeeshop.service;

import com.coffeeshop.dto.ProductDTO;
import com.coffeeshop.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    Optional<ProductDTO> getProductById(Long id);
    List<ProductDTO> getProductsByCategory(String category);

}
