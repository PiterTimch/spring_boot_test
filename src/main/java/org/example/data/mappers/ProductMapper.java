package org.example.data.mappers;

import org.example.data.data_transfer_objects.product.ProductCreateDTO;
import org.example.data.data_transfer_objects.product.ProductItemDTO;
import org.example.entities.product.CategoryEntity;
import org.example.entities.product.ProductEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ProductItemDTO toDTO(ProductEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "image", ignore = true) // встановимо нижче
    @Mapping(target = "deleted", constant = "false")
    ProductEntity fromCreateDTO(ProductCreateDTO dto,
                                @Context CategoryEntity category,
                                @Context String imageFileName);

    @AfterMapping
    default void setExtraFields(@MappingTarget ProductEntity entity,
                                @Context CategoryEntity category,
                                @Context String imageFileName) {
        entity.setCategory(category);
        entity.setImage(imageFileName);
    }
}
