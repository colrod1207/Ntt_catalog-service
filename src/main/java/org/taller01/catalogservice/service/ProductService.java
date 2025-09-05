package org.taller01.catalogservice.service;

import java.util.List;
import org.taller01.catalogservice.domain.Product;
import org.taller01.catalogservice.dto.ProductCreateDto;
import org.taller01.catalogservice.dto.ProductUpdateDto;
import org.taller01.catalogservice.dto.StockResponse;

public interface ProductService {
    List<Product> findAll();
    Product findById(String id);
    Product create(ProductCreateDto dto);
    Product update(String id, ProductUpdateDto dto);
    void delete(String id);
    StockResponse adjustStock(String id, int delta);
}
