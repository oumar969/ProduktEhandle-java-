package com.ehandle.repository;

import com.ehandle.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBySku(String sku);

    List<Product> findByCategory_Id(Long categoryId);

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Product> findByCategory_Id(Long categoryId, Pageable pageable);

    List<Product> findByIsActive(Boolean isActive);
}
