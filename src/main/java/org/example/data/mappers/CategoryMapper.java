package org.example.data.mappers;

import org.example.data.data_transfer_objects.product.CategoryDTO;
import org.example.entities.product.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDto(CategoryEntity category);
}
