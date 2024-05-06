package ru.mirea.MeatMarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.MeatMarket.entities.CartItem;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUsername(String username);

    void deleteCartItemByUsernameAndProductId(String username, Long id);

    void deleteCartItemByUsername(String username);

    CartItem findByUsernameAndProductId(String username, Long productId);
}
