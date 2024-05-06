package ru.mirea.MeatMarket.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mirea.MeatMarket.entities.CartItem;
import ru.mirea.MeatMarket.entities.Product;
import ru.mirea.MeatMarket.entities.WebUser;
import ru.mirea.MeatMarket.repositories.CartRepo;
import ru.mirea.MeatMarket.repositories.ProductRepo;
import ru.mirea.MeatMarket.services.CartService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class CartServiceTest {
    @Mock
    private CartRepo cartRepository;

    @Mock
    private ProductRepo productRepository;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddToCart() {
        WebUser user = new WebUser();
        Product product = new Product();
        int quantity = 2;

        cartService.addToCart(user, product, quantity);

        verify(cartRepository, times(1)).save(any(CartItem.class));
    }

    @Test
    public void testGetProductsByUsername() {
        String username = "testUser";
        when(cartRepository.findByUsername(username)).thenReturn(Arrays.asList(new CartItem(), new CartItem()));

        List<CartItem> cartItems = cartService.getProductsByUsername(username);

        assertEquals(2, cartItems.size());
    }

    @Test
    public void testDeleteProductByUsernameAndId() {
        String username = "testUser";
        Long productId = 1L;

        cartService.deleteProductByUsernameAndId(username, productId);

        verify(cartRepository, times(1)).deleteCartItemByUsernameAndProductId(username, productId);
    }

    @Test
    public void testDeleteProductByUsername() {
        String username = "testUser";

        cartService.deleteProductByUsername(username);

        verify(cartRepository, times(1)).deleteCartItemByUsername(username);
    }

    @Test
    public void testFindByUsernameAndId() {
        String username = "testUser";
        Long productId = 1L;
        CartItem cartItem = new CartItem();
        when(cartRepository.findByUsernameAndProductId(username, productId)).thenReturn(cartItem);

        CartItem result = cartService.findByUsernameAndId(username, productId);

        assertEquals(cartItem, result);
    }

    @Test
    void testCalculateTotalPrice() {
        // Создаем тестовые данные
        String username = "testUser";
        Long productId = 1L;
        double productPrice = 10.0;
        int quantity = 2;

        // Создаем моки для корзины и продукта
        CartItem cartItem = new CartItem();
        cartItem.setProductId(productId);
        cartItem.setQuantity(quantity);

        Product product = new Product();
        product.setId(productId);
        product.setPrice(productPrice);

        // Настраиваем моки
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);

        when(cartRepository.findByUsername(username)).thenReturn(cartItems);
        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(product));

        // Вызываем тестируемый метод
        double totalPrice = cartService.calculateTotalPrice(username);

        // Проверяем результат
        double expectedTotalPrice = productPrice * quantity;
        assertEquals(expectedTotalPrice, totalPrice);
    }
}

