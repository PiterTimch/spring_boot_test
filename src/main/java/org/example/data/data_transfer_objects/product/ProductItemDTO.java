package org.example.data.data_transfer_objects.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductItemDTO {

    private Long id;

    private String name;

    private String slug;

    private String description;

    private String image;

    private boolean isDeleted;

    private String categoryName;

    private Long categoryId;

}
