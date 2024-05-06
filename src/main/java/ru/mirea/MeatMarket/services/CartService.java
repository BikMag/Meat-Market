package ru.mirea.MeatMarket.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.MeatMarket.entities.CartItem;
import ru.mirea.MeatMarket.entities.Product;
import ru.mirea.MeatMarket.entities.WebUser;
import ru.mirea.MeatMarket.repositories.CartRepo;
import ru.mirea.MeatMarket.repositories.ProductRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CartService {
    private final CartRepo cartRepository;
    private final ProductRepo productRepository;

    public void addToCart(WebUser user, Product product, int quantity) {
        // Создаем новую запись в корзине
        CartItem cartItem = new CartItem();
        cartItem.setUsername(user.getUsername());
        cartItem.setProductId(product.getId());
        cartItem.setQuantity(quantity);

        // Сохраняем запись в базу данных
        cartRepository.save(cartItem);
    }

    public List<CartItem> getProductsByUsername(String username) {
        return cartRepository.findByUsername(username);
    }

    public void deleteProductByUsernameAndId(String username, Long id) {
        cartRepository.deleteCartItemByUsernameAndProductId(username, id);
    }

    public void deleteProductByUsername(String username) {
        cartRepository.deleteCartItemByUsername(username);
    }

    public CartItem findByUsernameAndId(String username, Long productId) {
        return cartRepository.findByUsernameAndProductId(username, productId);
    }

    public double calculateTotalPrice(String username) {
        double totalPrice = 0.0;

        List<CartItem> cartItems = cartRepository.findByUsername(username);
        for (CartItem cartItem : cartItems) {
            Product product = productRepository.findById(cartItem.getProductId()).orElse(null);
            if (product != null) {
                double itemPrice = product.getPrice() * cartItem.getQuantity();
                totalPrice += itemPrice;
            }
        }

        return totalPrice;
    }
}
