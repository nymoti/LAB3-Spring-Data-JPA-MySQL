package com.example.lab3.service;

import com.example.lab3.dto.CategoryDto;
import com.example.lab3.dto.ProductDto;
import com.example.lab3.entity.Category;
import com.example.lab3.repository.CategoryRepository;
import com.example.lab3.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository,
                           ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @Transactional
    public CategoryDto create(CategoryDto dto) {
        Category category = new Category();
        category.setName(dto.name());
        return toDto(categoryRepository.save(category));
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public CategoryDto findById(Long id) {
        return toDto(findCategory(id));
    }

    @Transactional(readOnly = true)
    public List<ProductDto> findProducts(Long categoryId) {
        findCategory(categoryId);
        return productRepository.findByCategoryId(categoryId).stream().map(productService::toDto).toList();
    }

    private Category findCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Catégorie introuvable avec l'id : " + id));
    }

    private CategoryDto toDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}
