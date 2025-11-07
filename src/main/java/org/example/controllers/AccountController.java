package org.example.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.data.data_transfer_objects.RegisterUserDTO;
import org.example.services.AccountService;
import org.example.services.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registerUserDTO", new RegisterUserDTO());
        return "account/register";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", accountService.GetAllUsers());
        return "account/users";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute RegisterUserDTO form,
                               BindingResult bindingResult,
                               HttpServletRequest request,
                               Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "Перевірте правильність введених даних");
            return "account/register";
        }

        boolean success = accountService.registerUser(form, request);

        if (success) {
            model.addAttribute("message", "Реєстрація успішна!");
            return "redirect:/users";
        } else {
            model.addAttribute("message", "Користувач із таким іменем або email вже існує.");
            return "account/register";
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        return "account/login";
    }

}
