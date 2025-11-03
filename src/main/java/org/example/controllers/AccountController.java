package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.services.AccountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;
import javax.imageio.ImageIO;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/register")
    public String register() {
        return "account/register";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", accountService.GetAllUsers());
        return "account/users";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam(required = false) MultipartFile imageFile,
                               Model model) {
        try {
            String imageFilename = null;

            if (imageFile != null && !imageFile.isEmpty()) {
                imageFilename = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();

                Path uploadDir = Paths.get(uploadPath);
                if (!uploadDir.isAbsolute()) {
                    uploadDir = Paths.get(System.getProperty("user.home"), uploadPath).toAbsolutePath();
                }

                Files.createDirectories(uploadDir);

                Path filePath = uploadDir.resolve(imageFilename);

                imageFile.transferTo(filePath.toFile());

                compressImage(filePath.toFile());
            }

            boolean success = accountService.registerUser(username, password, imageFilename);

            if (success) {
                model.addAttribute("message", "Реєстрація успішна!");
            } else {
                model.addAttribute("message", "Користувач із таким іменем вже існує.");
            }

        } catch (IOException e) {
            model.addAttribute("message", "Помилка при збереженні зображення!");
        }

        return "account/register";
    }


    private void compressImage(File file) throws IOException {
        BufferedImage original = ImageIO.read(file);
        if (original == null) return;

        int newWidth = 800;
        if (original.getWidth() <= newWidth) return;

        int newHeight = (original.getHeight() * newWidth) / original.getWidth();

        String ext = "jpg";
        String name = file.getName();
        if (name.lastIndexOf('.') > 0) {
            ext = name.substring(name.lastIndexOf('.') + 1).toLowerCase();
        }
        int imageType = "png".equals(ext) ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;

        BufferedImage resized = new BufferedImage(newWidth, newHeight, imageType);
        resized.getGraphics().drawImage(original, 0, 0, newWidth, newHeight, null);

        String newFileName = UUID.randomUUID().toString() + "." + ext;
        File newFile = new File(file.getParent(), newFileName);

        ImageIO.write(resized, ext, newFile);

        System.out.println("Збережено файл: " + newFile.getAbsolutePath());
    }
}
