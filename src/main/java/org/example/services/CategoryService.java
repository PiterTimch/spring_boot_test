package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.data.data_transfer_objects.product.CategoryDTO;
import org.example.data.mappers.CategoryMapper;
import org.example.entities.product.CategoryEntity;
import org.example.repository.ICategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final ICategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryDTO create(CategoryDTO dto) {
        if (categoryRepository.existsBySlug(dto.getSlug())) {
            throw new IllegalArgumentException("Категорія зі slug '" + dto.getSlug() + "' вже існує");
        }

        CategoryEntity entity = new CategoryEntity();
        entity.setName(dto.getName());
        entity.setSlug(dto.getSlug());

        CategoryEntity saved = categoryRepository.save(entity);
        return categoryMapper.toDto(saved);
    }

    public List<CategoryDTO> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public CategoryDTO getBySlug(String slug) {
        CategoryEntity entity = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new IllegalArgumentException("Категорію не знайдено: " + slug));
        return categoryMapper.toDto(entity);
    }
}