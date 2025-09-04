package org.taller01.catalogservice.service;

import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.taller01.catalogservice.domain.Product;
import org.taller01.catalogservice.dto.ProductCreateDto;
import org.taller01.catalogservice.dto.ProductInput;
import org.taller01.catalogservice.dto.ProductUpdateDto;
import org.taller01.catalogservice.exception.NotFoundException;
import org.taller01.catalogservice.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repo;

    @Override
    public List<Product> findAll() {
        return repo.findAll();
    }

    @Override
    public Product findById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
    }

    @Override
    public Product create(ProductCreateDto dto) {
        if (dto.stock() == null || dto.stock() <= 0) {
            throw new IllegalArgumentException("Product stock must be > 0");
        }
        validate(dto);
        Product p = Product.builder()
                .name(dto.name())
                .price(dto.price())
                .stock(dto.stock())
                .build();
        return repo.save(p);
    }

    @Override
    public Product update(String id, ProductUpdateDto dto) {
        Product product = repo.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
        validate(dto);
        product.setName(dto.name());
        product.setPrice(dto.price());
        return repo.save(product);
    }

    @Override
    public void delete(String id) {
        Product product = repo.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
        if (product.getStock() == null || product.getStock() > 0) {
            throw new IllegalStateException("Cannot delete product with stock > 0");
        }
        repo.deleteById(id);
    }

    void validate(ProductInput dto) {
        if (dto.name() == null || dto.name().isBlank()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (dto.price() == null || dto.price().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Product price must be > 0.00");
        }
    }

}
