package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.services.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/register")
    public String register() {
        return "account/register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name,
                               @RequestParam String password,
                               Model model) {

        boolean success = accountService.registerUser(name, password);

        if (success) {
            model.addAttribute("message", "Реєстрація успішна!");
        } else {
            model.addAttribute("message", "Користувач із таким іменем вже існує.");
        }

        return "account/register";
    }
}
