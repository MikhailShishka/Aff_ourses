package com.coffeeshop.repository;

import com.coffeeshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByAvailableTrue();

    // Метод для поиска по категории
    List<Product> findByCategory(String category);
}
