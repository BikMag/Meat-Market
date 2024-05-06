package ru.mirea.MeatMarket.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.mirea.MeatMarket.entities.Product;
import ru.mirea.MeatMarket.repositories.ProductRepo;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductService {
    @Autowired
    private final ProductRepo productRepo;

    public Product getProductByName(String name) {
        log.info("Get product by name {}", name);
        return productRepo.findByName(name).orElseThrow();
    }

    public Product getProductById(Long id) {
        log.info("Get product by id {}", id);
        return productRepo.findById(id).orElseThrow();
    }

    public List<Product> getAllProducts() {
        log.info("Get all products");
        return productRepo.findAll();
    }

    public void addProduct(Product product) {
        log.info("Add product {} to db", product);
        productRepo.save(product);
    }

    public Integer deleteProductById(Long id) {
        log.info("Delete product by id {}", id);
        return productRepo.deleteProductById(id).orElseThrow();
    }

    //    Фильтрация и сортировка данных
    public List<Product> sortProducts(String column, boolean reversed) {
        log.info("Sort products by order {} and reversed: {}", column, reversed);
        if (reversed)
            return productRepo.findAll(Sort.by(Sort.Direction.ASC, column).reverse());
        return productRepo.findAll(Sort.by(Sort.Direction.ASC, column));
    }

    public List<Product> filterAndSortProducts(String category, String sortBy, String sortDirection) {
        log.info("Filter products by category {}, sort by {} and sort direction {}", category, sortBy, sortDirection);
        List<Product> products = getAllProducts(); // Получить все товары из базы данных

        // Применить фильтрацию по категории
        if (category != null && !category.isEmpty()) {
            products = products.stream()
                    .filter(product -> product.getCategory().equals(category))
                    .collect(Collectors.toList());
        }

        // Применить сортировку
        if (sortBy != null) {
            Comparator<Product> comparator;
            switch (sortBy) {
                case "name":
                    comparator = Comparator.comparing(Product::getName);
                    break;
                case "price":
                    comparator = Comparator.comparing(Product::getPrice);
                    break;
                case "mass":
                    comparator = Comparator.comparing(Product::getMass);
                    break;
                default:
                    return products;
            }
            if (sortDirection.equals("desc"))
                comparator = comparator.reversed();

            products.sort(comparator);
        }

        return products;
    }

    public List<String> getAllCategories() {
        log.info("Get all categories");
        // Получаем все продукты из репозитория
        List<Product> allProducts = productRepo.findAll();

        // Используем Java Stream для извлечения уникальных категорий
        return allProducts.stream()
                .map(Product::getCategory) // Получаем категории из продуктов
                .distinct() // Удаляем дубликаты
                .collect(Collectors.toList());
    }
}
