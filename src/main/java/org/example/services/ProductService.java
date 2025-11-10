package org.example.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.data.data_transfer_objects.product.ProductCreateDTO;
import org.example.data.data_transfer_objects.product.ProductItemDTO;
import org.example.data.mappers.ProductMapper;
import org.example.entities.product.CategoryEntity;
import org.example.entities.product.ProductEntity;
import org.example.repository.ICategoryRepository;
import org.example.repository.IProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final FileService fileService;

    @Transactional
    public ProductItemDTO create(ProductCreateDTO dto) {
        CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Категорію не знайдено"));

        String imageFileName = null;
        if (dto.getImageFile() != null && !dto.getImageFile().isEmpty()) {
            imageFileName = fileService.load(dto.getImageFile());
        }
        else {
            imageFileName = fileService.load("https://loremflickr.com/800/600");
        }

        ProductEntity entity = productMapper.fromCreateDTO(dto, category, imageFileName);
        productRepository.save(entity);

        return productMapper.toDTO(entity);
    }

    public List<ProductItemDTO> getAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDTO)
                .toList();
    }
}