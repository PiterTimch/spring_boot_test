package org.example.data.mappers;

import org.example.data.data_transfer_objects.product.ProductCreateDTO;
import org.example.data.data_transfer_objects.product.ProductItemDTO;
import org.example.entities.product.CategoryEntity;
import org.example.entities.product.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class ProductMapper {

    public ProductItemDTO toDTO(ProductEntity entity) {
        if (entity == null) return null;

        ProductItemDTO dto = new ProductItemDTO();
        dto.setName(entity.getName());
        dto.setSlug(entity.getSlug());
        dto.setDescription(entity.getDescription());
        dto.setImage(entity.getImage());
        dto.setDeleted(entity.isDeleted());

        if (entity.getCategory() != null) {
            dto.setCategoryId(entity.getCategory().getId());
            dto.setCategoryName(entity.getCategory().getName());
        }

        return dto;
    }

    public ProductEntity fromCreateDTO(ProductCreateDTO dto, CategoryEntity category, String imageFileName) {
        ProductEntity entity = new ProductEntity();
        entity.setName(dto.getName());
        entity.setSlug(dto.getSlug());
        entity.setDescription(dto.getDescription());
        entity.setCategory(category);
        entity.setImage(imageFileName);
        entity.setDeleted(false);
        return entity;
    }
}