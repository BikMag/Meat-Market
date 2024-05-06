package ru.mirea.MeatMarket.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mirea.MeatMarket.entities.WebUser;
import ru.mirea.MeatMarket.services.WebUserService;

@RequestMapping("/register")
@RequiredArgsConstructor
@Controller
public class RegistrationController {
    private final WebUserService webUserService;

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new WebUser());

        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") WebUser webUser) {
        if (webUserService.getUserByName(webUser.getUsername()) == null) {
            webUserService.saveUser(webUser);
            return "redirect:/register?success";
        }
        return "redirect:/register?user_exists";
    }
}
