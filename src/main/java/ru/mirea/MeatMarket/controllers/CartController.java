package ru.mirea.MeatMarket.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mirea.MeatMarket.entities.CartItem;
import ru.mirea.MeatMarket.entities.Product;
import ru.mirea.MeatMarket.entities.WebUser;
import ru.mirea.MeatMarket.services.CartService;
import ru.mirea.MeatMarket.services.EmailService;
import ru.mirea.MeatMarket.services.ProductService;
import ru.mirea.MeatMarket.services.WebUserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    @Autowired
    private final ProductService productService;
    @Autowired
    private final CartService cartService;
    @Autowired
    private final WebUserService userService;
    @Autowired
    private final EmailService emailService;

    @GetMapping("/cart")
    public String showCatalog(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", username);

        // Получаем все записи корзины для текущего пользователя
        List<CartItem> cartItems = cartService.getProductsByUsername(username);

        // Создаем список для хранения данных о продуктах
        List<Product> products = new ArrayList<>();

        // Для каждой записи в корзине получаем данные о продукте и добавляем их в список продуктов
        for (CartItem cartItem : cartItems) {
            Product product = productService.getProductById(cartItem.getProductId());
            products.add(product);
        }

        // Передаем список продуктов в представление
        model.addAttribute("products", products);

        double totalPrice = cartService.calculateTotalPrice(username);
        model.addAttribute("total_price", totalPrice);

        // Отображаем представление корзины
        return "cart";
    }

    @Transactional
    @DeleteMapping("/deleteProduct/{id}")
    @ResponseBody
    public String deleteProduct(@PathVariable Long id) {
        System.out.println("Удаление товара из корзины с id " + id);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        cartService.deleteProductByUsernameAndId(username, id);

        return "redirect:/cart";
    }

    // Оформляем заказ: отправляем письмо на почту и удаляем купленные товары
    @PostMapping("/checkout")
    public String placeOrder() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        WebUser user = userService.getUserByName(username);

        String message = username + ", ваш заказ был оформлен! В скором времени мы отправим его к вам!";
        emailService.sendEmail(user.getEmail(), "Ваш заказ оформлен", message);

        cartService.deleteProductByUsername(username);

        return "redirect:/home?checkout";
    }
}
