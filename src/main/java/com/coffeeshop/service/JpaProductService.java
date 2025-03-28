package com.coffeeshop.service;

import com.coffeeshop.dto.ProductDTO;
import com.coffeeshop.mapper.ProductMapper;
import com.coffeeshop.model.Product;
import com.coffeeshop.repository.CategoryRepository;
import com.coffeeshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JpaProductService implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::convertToDTO);
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImageUrl(product.getImageUrl());
        dto.setAvailable(product.isAvailable());
        return dto;
    }

    // Получить продукты по категории
    public List<ProductDTO> getProductsByCategoryName(String categoryName) {
        return productRepository.findByCategoryName(categoryName).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> searchByNameAndPriceRange(String namePart, BigDecimal min, BigDecimal max) {
        return productRepository.searchByNameAndPriceRange(namePart, min, max);
    }

    private ProductMapper productMapper;

    @Autowired
    public JpaProductService(ProductRepository productRepository,
                             ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }


    @Override
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toDTO);
    }

    @Override
    public Page<ProductDTO> getProductsByCategoryName(String categoryName, Pageable pageable) {
        return productRepository.findByCategoryName(categoryName, pageable)
                .map(productMapper::toDTO);
    }
}
