package ru.mirea.MeatMarket.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.mirea.MeatMarket.entities.Product;
import ru.mirea.MeatMarket.services.ProductService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    @GetMapping
    @ResponseBody
    public List<Product> getPhones() {
        return service.getAllProducts();
    }

    @PostMapping
    @ResponseBody
    public Product addProduct(
            @RequestBody Product product
    ) {
        service.addProduct(product);
        return product;
    }

    @Transactional
    @DeleteMapping("/{id}")
    @ResponseBody
    public String deleteProduct(@PathVariable Long id) {
        return "Count: " + service.deleteProductById(id);
    }

    @GetMapping(value = "/phone/{name}")
    @ResponseBody
    public Product getByName(@PathVariable("name") String name) {
        return service.getProductByName(name);
    }

    //  Фильтрация (сортировка) данных
//    @GetMapping("/sort/{column}")
//    @ResponseBody
//    public List<Product> sortProduct(@PathVariable("column") String column) {
//        return service.sortProducts(column, false);
//    }

    static class Details {
        String columnName;
        boolean reversed;

        public Details(String columnName, boolean reversed) {
            this.columnName = columnName;
            this.reversed = reversed;
        }
    }

    @PostMapping("/sort")
    @ResponseBody
    public List<Product> sortProducts(@RequestBody Details details) {
        return service.sortProducts(details.columnName, details.reversed);
    }
}
