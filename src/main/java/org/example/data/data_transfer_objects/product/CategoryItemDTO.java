package org.example.data.data_transfer_objects.product;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryItemDTO {
    private Long id;

    private String name;

    private String slug;
}
