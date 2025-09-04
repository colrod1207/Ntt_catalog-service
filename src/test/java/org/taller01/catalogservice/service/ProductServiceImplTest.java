package org.taller01.catalogservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.taller01.catalogservice.domain.Product;
import org.taller01.catalogservice.dto.ProductCreateDto;
import org.taller01.catalogservice.dto.ProductUpdateDto;
import org.taller01.catalogservice.exception.NotFoundException;
import org.taller01.catalogservice.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository repo;

    @InjectMocks
    private ProductServiceImpl service;

    @Test
    void shouldCreateProductSuccessfully() {
        ProductCreateDto dto = new ProductCreateDto("Coffee", new BigDecimal("39.99"), 100);
        Product expected = Product.builder()
                .name(dto.name())
                .price(dto.price())
                .stock(dto.stock())
                .build();

        when(repo.save(any())).thenReturn(expected);

        Product result = service.create(dto);

        assertEquals("Coffee", result.getName());
        assertEquals(new BigDecimal("39.99"), result.getPrice());
        assertEquals(100, result.getStock());
    }

    @Test
    void shouldThrowExceptionWhenStockIsInvalid() {
        ProductCreateDto dto = new ProductCreateDto("Coffee", new BigDecimal("39.99"), 0);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.create(dto));
        assertTrue(ex.getMessage().contains("Product stock must be > 0"));
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        Product existing = new Product("1", "Coffee", new BigDecimal("39.99"), 100);
        ProductUpdateDto dto = new ProductUpdateDto("Coffee Premium", new BigDecimal("49.99"));

        when(repo.findById("1")).thenReturn(Optional.of(existing));
        when(repo.save(any())).thenReturn(existing);

        Product result = service.update("1", dto);

        assertEquals("Coffee Premium", result.getName());
        assertEquals(new BigDecimal("49.99"), result.getPrice());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonexistentProduct() {
        ProductUpdateDto dto = new ProductUpdateDto("Coffee", new BigDecimal("39.99"));
        when(repo.findById("999")).thenReturn(Optional.empty());

        Exception ex = assertThrows(NotFoundException.class, () -> service.update("999", dto));
        assertEquals("999", ex.getMessage());
    }

    @Test
    void shouldReturnAllProducts() {
        List<Product> products = List.of(
                new Product("1", "Coffee", new BigDecimal("39.99"), 100),
                new Product("2", "Tea", new BigDecimal("29.99"), 50)
        );

        when(repo.findAll()).thenReturn(products);

        List<Product> result = service.findAll();

        assertEquals(2, result.size());
        assertEquals("Coffee", result.get(0).getName());
    }

    @Test
    void shouldReturnProductById() {
        Product product = new Product("1", "Coffee", new BigDecimal("39.99"), 100);
        when(repo.findById("1")).thenReturn(Optional.of(product));

        Product result = service.findById("1");

        assertEquals("Coffee", result.getName());
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        when(repo.findById("999")).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.findById("999"));
        assertTrue(ex.getMessage().contains("Product not found"));
    }

    @Test
    void shouldDeleteProductSuccessfully() {
        when(repo.existsById("1")).thenReturn(true);
        doNothing().when(repo).deleteById("1");

        assertDoesNotThrow(() -> service.delete("1"));
        verify(repo).deleteById("1");
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonexistentProduct() {
        when(repo.existsById("999")).thenReturn(false);

        Exception ex = assertThrows(NotFoundException.class, () -> service.delete("999"));
        assertEquals("999", ex.getMessage());
    }
}