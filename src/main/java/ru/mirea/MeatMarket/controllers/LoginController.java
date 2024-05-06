package ru.mirea.MeatMarket.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/login")
@Controller
public class LoginController {
    @GetMapping
    public String showLoginForm() {
        return "login";
    }
}
