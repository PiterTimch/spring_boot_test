package org.example.data;

import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.example.data.constants.RolesConstants;
import org.example.data.data_transfer_objects.product.CategoryDTO;
import org.example.entities.account.RoleEntity;
import org.example.repository.IRoleRepository;
import org.example.services.CategoryService;
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

    private final Faker faker = new Faker(new Locale("uk"));
    private final Random random = new Random();
    private final Slugify slugify = Slugify.builder()
            .locale(Locale.forLanguageTag("uk"))
            .transliterator(true)
            .build();

    @PostConstruct
    public void seedData() {
        seedRoles();
        seedCategories();
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
                CategoryDTO dto = new CategoryDTO();
                dto.setName(name);
                dto.setSlug(slug);

                categoryService.create(dto);
                System.out.println("Додано категорію: " + name + " (" + slug + ")");
            } catch (IllegalArgumentException e) {
                i--;
            }
        }
    }
}
