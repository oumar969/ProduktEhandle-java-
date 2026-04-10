package com.ehandle.util;

import com.ehandle.dto.ProductDTO;
import com.ehandle.model.Category;
import com.ehandle.model.Product;
import com.ehandle.repository.CategoryRepository;
import com.ehandle.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

import java.util.Arrays;
import java.util.List;

/**
 * Initialize database with dummy data on startup
 */
@Configuration
public class DataInitializer {
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner loadData(CategoryRepository categoryRepository, ProductRepository productRepository) {
        return args -> {
            // Check if data already exists
            if (categoryRepository.count() > 0 || productRepository.count() > 0) {
                logger.info("Database already has data. Skipping initialization.");
                return;
            }

            logger.info("Initializing database with dummy data...");

            // Create categories
            Category cat1 = Category.builder()
                    .name("Electronics")
                    .description("Electronic devices and accessories")
                    .build();

            Category cat2 = Category.builder()
                    .name("Furniture")
                    .description("Home and office furniture")
                    .build();

            Category cat3 = Category.builder()
                    .name("Clothing")
                    .description("Apparel and fashion items")
                    .build();

            Category cat4 = Category.builder()
                    .name("Books")
                    .description("Educational and entertainment books")
                    .build();

            Category cat5 = Category.builder()
                    .name("Sports")
                    .description("Sports and fitness equipment")
                    .build();

            List<Category> categories = Arrays.asList(cat1, cat2, cat3, cat4, cat5);
            categoryRepository.saveAll(categories);
            logger.info("Created {} categories", categories.size());

            // Create products
            List<Product> products = Arrays.asList(
                    Product.builder()
                            .name("Wireless Headphones")
                            .description("Premium Bluetooth wireless headphones with noise cancellation")
                            .price(BigDecimal.valueOf(149.99))
                            .quantityInStock(50)
                            .sku("WH001")
                            .category(cat1)
                            .build(),
                    Product.builder()
                            .name("USB-C Fast Charging Cable")
                            .description("High-speed USB-C cable for fast charging")
                            .price(BigDecimal.valueOf(14.99))
                            .quantityInStock(200)
                            .sku("USB-C001")
                            .category(cat1)
                            .build(),
                    Product.builder()
                            .name("4K Monitor")
                            .description("27-inch 4K UltraHD monitor")
                            .price(BigDecimal.valueOf(599.99))
                            .quantityInStock(20)
                            .sku("MON001")
                            .category(cat1)
                            .build(),
                    Product.builder()
                            .name("Mechanical Keyboard RGB")
                            .description("Gaming mechanical keyboard with RGB lighting")
                            .price(BigDecimal.valueOf(149.99))
                            .quantityInStock(45)
                            .sku("KB001")
                            .category(cat1)
                            .build(),
                    Product.builder()
                            .name("Wireless Mouse")
                            .description("Ergonomic wireless mouse with precision tracking")
                            .price(BigDecimal.valueOf(49.99))
                            .quantityInStock(80)
                            .sku("MOUSE001")
                            .category(cat1)
                            .build(),
                    Product.builder()
                            .name("Executive Office Chair")
                            .description("Comfortable ergonomic office chair with lumbar support")
                            .price(BigDecimal.valueOf(399.99))
                            .quantityInStock(15)
                            .sku("CHAIR001")
                            .category(cat2)
                            .build(),
                    Product.builder()
                            .name("Standing Desk Converter")
                            .description("Electric standing desk converter for health benefits")
                            .price(BigDecimal.valueOf(299.99))
                            .quantityInStock(25)
                            .sku("DESK001")
                            .category(cat2)
                            .build(),
                    Product.builder()
                            .name("Bookshelves Set")
                            .description("SET of 3 wooden bookshelves")
                            .price(BigDecimal.valueOf(179.99))
                            .quantityInStock(30)
                            .sku("SHELF001")
                            .category(cat2)
                            .build(),
                    Product.builder()
                            .name("Cotton T-Shirt Pack")
                            .description("Pack of 5 premium cotton t-shirts")
                            .price(BigDecimal.valueOf(59.99))
                            .quantityInStock(120)
                            .sku("TSHIRT001")
                            .category(cat3)
                            .build(),
                    Product.builder()
                            .name("Blue Denim Jeans")
                            .description("Classic blue denim jeans for all occasions")
                            .price(BigDecimal.valueOf(79.99))
                            .quantityInStock(100)
                            .sku("JEANS001")
                            .category(cat3)
                            .build(),
                    Product.builder()
                            .name("Winter Jacket")
                            .description("Warm winter parka jacket with insulation")
                            .price(BigDecimal.valueOf(249.99))
                            .quantityInStock(35)
                            .sku("JACKET001")
                            .category(cat3)
                            .build(),
                    Product.builder()
                            .name("Java Programming Guide")
                            .description("Complete guide to Java programming from basics to advanced")
                            .price(BigDecimal.valueOf(49.99))
                            .quantityInStock(150)
                            .sku("BOOK001")
                            .category(cat4)
                            .build(),
                    Product.builder()
                            .name("Clean Code Book")
                            .description("Essential software craftsmanship guide")
                            .price(BigDecimal.valueOf(39.99))
                            .quantityInStock(100)
                            .sku("BOOK002")
                            .category(cat4)
                            .build(),
                    Product.builder()
                            .name("Python Crash Course")
                            .description("Learn Python programming from zero to hero")
                            .price(BigDecimal.valueOf(44.99))
                            .quantityInStock(120)
                            .sku("BOOK003")
                            .category(cat4)
                            .build(),
                    Product.builder()
                            .name("Running Shoes Pro")
                            .description("Professional grade running shoes for marathons")
                            .price(BigDecimal.valueOf(189.99))
                            .quantityInStock(40)
                            .sku("SHOES001")
                            .category(cat5)
                            .build(),
                    Product.builder()
                            .name("Yoga Mat Non-Slip")
                            .description("Premium non-slip yoga mat for workouts")
                            .price(BigDecimal.valueOf(39.99))
                            .quantityInStock(90)
                            .sku("YOGA001")
                            .category(cat5)
                            .build(),
                    Product.builder()
                            .name("Dumbbell Set 5-30 lbs")
                            .description("Complete adjustable dumbbell set for home gym")
                            .price(BigDecimal.valueOf(299.99))
                            .quantityInStock(20)
                            .sku("DUMB001")
                            .category(cat5)
                            .build(),
                    Product.builder()
                            .name("Smart Watch Fitness Tracker")
                            .description("Wearable smartwatch with fitness tracking and heart rate monitor")
                            .price(BigDecimal.valueOf(199.99))
                            .quantityInStock(60)
                            .sku("WATCH001")
                            .category(cat5)
                            .build()
            );

            productRepository.saveAll(products);
            logger.info("Created {} products", products.size());
            logger.info("Database initialization complete!");
        };
    }
}
