package org.example.data;

import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.example.data.constants.RolesConstants;
import org.example.data.data_transfer_objects.product.CategoryCreateDTO;
import org.example.data.data_transfer_objects.product.CategoryItemDTO;
import org.example.entities.account.RoleEntity;
import org.example.repository.IRoleRepository;
import org.example.services.CategoryService;
import org.example.services.FileService;
import org.example.services.ProductService;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class AppDbSeeder {

    private final IRoleRepository roleRepository;
    private final CategoryService categoryService;
    private final ProductService productService;

    private final Faker faker = new Faker(new Locale("uk"));
    private final Random random = new Random();
    private final Slugify slugify = Slugify.builder()
            .locale(Locale.forLanguageTag("uk"))
            .transliterator(true)
            .build();
    private final FileService fileService;

    @PostConstruct
    public void seedData() {
        seedRoles();
        seedCategories();
        seedProducts();
    }

    private void seedRoles() {
        List<String> roles = RolesConstants.Roles;

        for (String roleName : roles) {
            boolean exists = roleRepository.findByName(roleName).isPresent();
            if (!exists) {
                RoleEntity role = new RoleEntity();
                role.setName(roleName);
                roleRepository.save(role);
                System.out.println("Додано роль: " + roleName);
            } else {
                System.out.println("Роль уже існує: " + roleName);
            }
        }
    }

    private void seedCategories() {
        int targetCount = 10;

        long existingCount = categoryService.getAll().size();
        if (existingCount >= targetCount) {
            return;
        }

        for (int i = 0; i < targetCount; i++) {
            String name = faker.commerce().department();
            String slug = slugify.slugify(name);

            try {
                CategoryCreateDTO dto = new CategoryCreateDTO();
                dto.setName(name);
                dto.setSlug(slug);

                categoryService.create(dto);
                System.out.println("Додано категорію: " + name + " (" + slug + ")");
            } catch (IllegalArgumentException e) {
                i--;
            }
        }
    }

    private void seedProducts() {
        if (productService.getAll().isEmpty()) {
            int targetCount = 50;

            var categories = categoryService.getAll();
            if (categories.isEmpty()) {
                System.out.println("Пропускаю продукти — немає категорій.");
                return;
            }

            long existingCount = categoryService.getAll().size();
            if (existingCount >= targetCount) {
                System.out.println("Продукти вже існують, сід пропущено.");
                return;
            }

            System.out.println("Починаю створювати " + targetCount + " продуктів...");

            for (int i = 0; i < targetCount; i++) {
                try {
                    String name = faker.commerce().productName();
                    String slug = slugify.slugify(name);
                    String description = faker.lorem().sentence(10);

                    var category = categories.get(random.nextInt(categories.size()));

                    var dto = new org.example.data.data_transfer_objects.product.ProductCreateDTO();
                    dto.setName(name);
                    dto.setSlug(slug);
                    dto.setDescription(description);
                    dto.setCategoryId(category.getId());

                    dto.setImageFile(null);

                    productService.create(dto);

                    System.out.printf("Продукт %d: %s (%s)%n", i + 1, name, category.getName());
                } catch (Exception e) {
                    System.out.println("Помилка при створенні продукту: " + e.getMessage());
                    i--;
                }
            }

            System.out.println("Сід продуктів завершено!");
        }

    }

}
