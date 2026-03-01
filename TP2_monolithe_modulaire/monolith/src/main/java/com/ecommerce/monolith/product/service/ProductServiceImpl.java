package com.ecommerce.monolith.product.service;


import com.ecommerce.monolith.product.model.Product;
import com.ecommerce.monolith.product.repository.ProductRepository;
import com.ecommerce.monolith.product.dto.CreateProductRequest;
import com.ecommerce.monolith.product.dto.ProductDTO;
import com.ecommerce.monolith.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Override
    public List<ProductDTO> getAllProducts() {
        return mapper.toDTOList(repository.findAll());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        return mapper.toDTO(product);
    }

    @Override
    public ProductDTO createProduct(CreateProductRequest request) {
        Product product = mapper.toEntity(request);
        Product saved = repository.save(product);
        return mapper.toDTO(saved);
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(Long id, CreateProductRequest request) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        mapper.updateEntity(request, product);
        return mapper.toDTO(product);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Product not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
