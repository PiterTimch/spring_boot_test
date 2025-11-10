package org.example.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.data.data_transfer_objects.account.ForgotPasswordDTO;
import org.example.data.data_transfer_objects.account.RegisterUserDTO;
import org.example.data.data_transfer_objects.account.ResetPasswordDTO;
import org.example.services.AccountService;
import org.example.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductService productService;

    @GetMapping("/list")
    public String listUsers(Model model) {
        model.addAttribute("products", productService.getAll());
        return "products/list";
    }
}