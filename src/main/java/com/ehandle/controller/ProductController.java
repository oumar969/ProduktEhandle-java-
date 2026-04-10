package com.ehandle.controller;

import com.ehandle.dto.ProductDTO;
import com.ehandle.model.Category;
import com.ehandle.model.Product;
import com.ehandle.service.CategoryService;
import com.ehandle.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Product management endpoints")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieve all products with pagination")
    public ResponseEntity<Page<ProductDTO>> getAllProducts(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ProductDTO> products = productService.getAllProducts(pageable)
                .map(productService::toDTO);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieve a specific product")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(productService.toDTO(product));
    }

    @GetMapping("/search")
    @Operation(summary = "Search products", description = "Search products by name")
    public ResponseEntity<Page<ProductDTO>> searchProducts(
            @RequestParam String name,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("Searching products with name: {}", name);
        Page<ProductDTO> products = productService.searchProducts(name, pageable)
                .map(productService::toDTO);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get products by category", description = "Retrieve all products in a specific category")
    public ResponseEntity<Page<ProductDTO>> getProductsByCategory(
            @PathVariable Long categoryId,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ProductDTO> products = productService.getProductsByCategory(categoryId, pageable)
                .map(productService::toDTO);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    @Operation(summary = "Create new product", description = "Create a new product")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        log.info("Creating new product: {}", productDTO.getName());
        if (productDTO.getImageUrl() != null && !productDTO.getImageUrl().isEmpty()) {
            log.info("Product has image data: {} bytes", productDTO.getImageUrl().length());
        } else {
            log.info("No image data provided");
        }
        Product product = productService.toEntity(productDTO);

        Category category = categoryService.getCategoryById(productDTO.getCategoryId());
        product.setCategory(category);

        Product created = productService.createProduct(product);
        return new ResponseEntity<>(productService.toDTO(created), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Update an existing product")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDTO productDTO) {
        log.info("Updating product: {}", id);
        Product product = productService.toEntity(productDTO);

        if (productDTO.getCategoryId() != null) {
            Category category = categoryService.getCategoryById(productDTO.getCategoryId());
            product.setCategory(category);
        }

        Product updated = productService.updateProduct(id, product);
        return ResponseEntity.ok(productService.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Delete a product")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.info("Deleting product: {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/stock")
    @Operation(summary = "Update product stock", description = "Update the stock quantity of a product")
    public ResponseEntity<ProductDTO> updateProductStock(
            @PathVariable Long id,
            @RequestParam Integer quantity) {
        log.info("Updating stock for product: {} to {}", id, quantity);
        Product product = productService.getProductById(id);
        product.setQuantityInStock(quantity);
        Product updated = productService.updateProduct(id, product);
        return ResponseEntity.ok(productService.toDTO(updated));
    }
}
