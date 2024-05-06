package ru.mirea.MeatMarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.MeatMarket.entities.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo  extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    Optional<List<Product>> findAllByCategory(String category);
    Optional<Integer> deleteProductById(Long id);
}
