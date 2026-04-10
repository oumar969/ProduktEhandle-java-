package com.ehandle.service;

import com.ehandle.dto.ProductDTO;
import com.ehandle.exception.BadRequestException;
import com.ehandle.exception.ResourceNotFoundException;
import com.ehandle.model.Category;
import com.ehandle.model.Product;
import com.ehandle.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Product> searchProducts(String name, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategory_Id(categoryId);
    }

    public Page<Product> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findByCategory_Id(categoryId, pageable);
    }

    public Product createProduct(Product product) {
        if (product.getSku() != null && productRepository.findBySku(product.getSku()).isPresent()) {
            throw new BadRequestException("Product with SKU " + product.getSku() + " already exists");
        }

        Category category = categoryService.getCategoryById(product.getCategory().getId());
        product.setCategory(category);

        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = getProductById(id);

        if (productDetails.getName() != null) {
            product.setName(productDetails.getName());
        }

        if (productDetails.getDescription() != null) {
            product.setDescription(productDetails.getDescription());
        }

        if (productDetails.getPrice() != null) {
            product.setPrice(productDetails.getPrice());
        }

        if (productDetails.getQuantityInStock() != null) {
            product.setQuantityInStock(productDetails.getQuantityInStock());
        }

        if (productDetails.getSku() != null && !productDetails.getSku().equals(product.getSku())) {
            if (productRepository.findBySku(productDetails.getSku()).isPresent()) {
                throw new BadRequestException("Product with SKU " + productDetails.getSku() + " already exists");
            }
            product.setSku(productDetails.getSku());
        }

        if (productDetails.getIsActive() != null) {
            product.setIsActive(productDetails.getIsActive());
        }

        if (productDetails.getCategory() != null) {
            Category category = categoryService.getCategoryById(productDetails.getCategory().getId());
            product.setCategory(category);
        }

        if (productDetails.getImageUrl() != null) {
            product.setImageUrl(productDetails.getImageUrl());
        }

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    public ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantityInStock(product.getQuantityInStock())
                .sku(product.getSku())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .imageUrl(product.getImageUrl())
                .isActive(product.getIsActive())
                .build();
    }

    public Product toEntity(ProductDTO productDTO) {
        return Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .quantityInStock(productDTO.getQuantityInStock())
                .sku(productDTO.getSku())
                .imageUrl(productDTO.getImageUrl())
                .isActive(productDTO.getIsActive() != null ? productDTO.getIsActive() : true)
                .build();
    }
}
