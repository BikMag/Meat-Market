package ru.mirea.MeatMarket.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping({"/", "/home"})
    public String getHome(Model model) {
        model.addAttribute("username", SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName()
        );
        return "home";
    }
}
