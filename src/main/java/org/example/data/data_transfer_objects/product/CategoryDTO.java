package org.example.data.data_transfer_objects.product;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
    @NotBlank(message = "Ім'я обов'язкове")
    private String name;

    @NotBlank(message = "Slug обов'язковий")
    private String slug;
}
