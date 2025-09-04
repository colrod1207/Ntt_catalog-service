package org.taller01.catalogservice.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.taller01.catalogservice.domain.Product;
import org.taller01.catalogservice.dto.ProductCreateUpdateDto;
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
    public Product create(ProductCreateUpdateDto dto) {
        Product p = Product.builder()
                .name(dto.name())
                .price(dto.price())
                .build();
        return repo.save(p);
    }

    @Override
    public Product update(String id, ProductCreateUpdateDto dto) {
        Product p = findById(id);
        p.setName(dto.name());
        p.setPrice(dto.price());
        return repo.save(p);
    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }
}
