package org.example.data.mappers;

import org.example.data.data_transfer_objects.product.CategoryCreateDTO;
import org.example.data.data_transfer_objects.product.CategoryItemDTO;
import org.example.entities.product.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryItemDTO toDto(CategoryEntity category);

    CategoryEntity fromCreateDTO(CategoryCreateDTO dto);
}
