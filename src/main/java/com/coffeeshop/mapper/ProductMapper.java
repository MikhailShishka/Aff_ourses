package com.coffeeshop.mapper;

import com.coffeeshop.dto.ProductDTO;
import com.coffeeshop.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDTO(Product product);
    Product toEntity(ProductDTO dto);
}
