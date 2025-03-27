package com.coffeeshop.repository;

import com.coffeeshop.dto.ProductDTO;
import com.coffeeshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByAvailableTrue();

    // Метод для поиска по категории
    List<Product> findByCategoryName(String categoryName);

    // Новый кастомный JPA-запрос: часть названия и диапазон цен
    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :namePart, '%')) " +
            "AND p.price BETWEEN :minPrice AND :maxPrice")
    List<ProductDTO> searchByNameAndPriceRange(@Param("namePart") String namePart,
                                               @Param("minPrice") BigDecimal minPrice,
                                               @Param("maxPrice") BigDecimal maxPrice);
}
