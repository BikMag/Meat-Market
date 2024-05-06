package ru.mirea.MeatMarket.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.mirea.MeatMarket.entities.Product;
import ru.mirea.MeatMarket.repositories.ProductRepo;

import java.util.List;

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

    //    Фильтрация (сортировка) данных
    public List<Product> sortProducts(String column, boolean reversed) {
        log.info("Sort products by order {}", column);
        if (reversed)
            return productRepo.findAll(Sort.by(Sort.Direction.ASC, column).reverse());
        return productRepo.findAll(Sort.by(Sort.Direction.ASC, column));
    }
}
