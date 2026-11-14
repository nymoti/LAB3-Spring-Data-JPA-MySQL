package com.example.lab3.service;

import com.example.lab3.dto.ProductDto;
import com.example.lab3.entity.Category;
import com.example.lab3.entity.Product;
import com.example.lab3.repository.CategoryRepository;
import com.example.lab3.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public ProductDto create(ProductDto dto) {
        Product product = new Product();
        copy(dto, product);
        return toDto(productRepository.save(product));
    }

    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        return toDto(findProduct(id));
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(this::toDto);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> search(String name) {
        return productRepository.findByNameContainingIgnoreCase(name).stream().map(this::toDto).toList();
    }

    @Transactional
    public ProductDto update(Long id, ProductDto dto) {
        Product product = findProduct(id);
        copy(dto, product);
        return toDto(productRepository.save(product));
    }

    @Transactional
    public void delete(Long id) {
        productRepository.delete(findProduct(id));
        productRepository.flush();
    }

    // Appelée dans une méthode @Transactional : la catégorie LAZY peut encore être chargée
    ProductDto toDto(Product product) {
        Category category = product.getCategory();
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                category != null ? category.getId() : null,
                category != null ? category.getName() : null);
    }

    private void copy(ProductDto dto, Product product) {
        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Catégorie introuvable avec l'id : " + dto.categoryId()));
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setCategory(category);
    }

    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produit introuvable avec l'id : " + id));
    }
}
