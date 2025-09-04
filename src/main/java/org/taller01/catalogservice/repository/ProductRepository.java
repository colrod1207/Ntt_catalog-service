package org.taller01.catalogservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.taller01.catalogservice.domain.Product;

public interface ProductRepository extends MongoRepository<Product, String> {}
