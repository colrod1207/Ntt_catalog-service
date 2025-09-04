package org.taller01.catalogservice.service;

import java.util.List;
import org.taller01.catalogservice.domain.Product;
import org.taller01.catalogservice.dto.ProductCreateUpdateDto;

public interface ProductService {
    List<Product> findAll();
    Product findById(String id);
    Product create(ProductCreateUpdateDto dto);
    Product update(String id, ProductCreateUpdateDto dto);
    void delete(String id);
}
