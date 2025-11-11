package org.example.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.data.data_transfer_objects.product.ProductCreateDTO;
import org.example.data.data_transfer_objects.product.ProductItemDTO;
import org.example.data.mappers.ProductMapper;
import org.example.entities.product.CategoryEntity;
import org.example.entities.product.ImageEntity;
import org.example.entities.product.ProductEntity;
import org.example.repository.ICategoryRepository;
import org.example.repository.IProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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

        ProductEntity entity = productMapper.fromCreateDTO(dto, category);

        List<ImageEntity> imageEntities = new ArrayList<>();

        if (dto.getImageFiles() != null && !dto.getImageFiles().isEmpty()) {
            short priority = 0;
            for (MultipartFile file : dto.getImageFiles()) {
                String fileName = fileService.load(file);

                ImageEntity image = new ImageEntity();
                image.setName(fileName);
                image.setPriority(priority++);
                image.setProduct(entity);

                imageEntities.add(image);
            }
        } else {
            String fallbackName = fileService.load("https://loremflickr.com/800/600");
            ImageEntity image = new ImageEntity();
            image.setName(fallbackName);
            image.setPriority((short) 0);
            image.setProduct(entity);
            imageEntities.add(image);
        }

        entity.setImages(imageEntities);
        productRepository.save(entity);

        return productMapper.toDTO(entity);
    }

    @Transactional
    public List<ProductItemDTO> getAll() {
        return productRepository.findAll()
                .stream()
                .filter(x -> !x.isDeleted())
                .map(productMapper::toDTO)
                .toList();
    }
}