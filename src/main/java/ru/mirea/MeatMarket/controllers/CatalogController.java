package ru.mirea.MeatMarket.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mirea.MeatMarket.entities.CartItem;
import ru.mirea.MeatMarket.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mirea.MeatMarket.entities.WebUser;
import ru.mirea.MeatMarket.services.CartService;
import ru.mirea.MeatMarket.services.ProductService;
import ru.mirea.MeatMarket.services.WebUserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CatalogController {
    @Autowired
    private final ProductService productService;
    @Autowired
    private final WebUserService userService;
    @Autowired
    private final CartService cartService;

    @GetMapping("/catalog")
    public String showCatalog(Model model,
                              @RequestParam(value = "category", required = false) String category,
                              @RequestParam(name = "sortBy", required = false) String sortBy,
                              @RequestParam(name = "sortDirection", required = false, defaultValue = "asc") String sortDirection) {

        // Получаем все товары или фильтруем по категории и сортируем
        List<Product> products = productService.filterAndSortProducts(category, sortBy, sortDirection);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("username", username);
        model.addAttribute("products", products);
        model.addAttribute("cart", cartService.getProductsByUsername(username));
        model.addAttribute("categories", productService.getAllCategories());

        return "catalog";
    }

    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("productId") Long productId) {
        // Получаем имя авторизованного пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Получаем информацию о товаре по его идентификатору и покупателя по его имени
        Product product = productService.getProductById(productId);
        WebUser user = userService.getUserByName(username);

        // Добавляем товар в корзину для указанного пользователя
        CartItem cartItem = cartService.findByUsernameAndId(username, productId);
        // Если такой товар уже есть - увеличиваем значение quantity у существующей записи
        if (cartItem != null)
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        else
            cartService.addToCart(user, product, 1);

        // Перенаправляем пользователя на страницу каталога товаров после добавления в корзину
        return "redirect:/catalog";
    }
}
