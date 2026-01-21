package ua.kpi.cosmocats.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kpi.cosmocats.dto.ProductDTO;
import ua.kpi.cosmocats.domain.Product;
import ua.kpi.cosmocats.mapper.ProductMapper;
import ua.kpi.cosmocats.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor // Автоматично створює конструктор для final полів (ін'єкція залежностей)
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    // GET: Отримати всі продукти
    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.findAll().stream()
                .map(productMapper::toDto)
                .toList();
    }

    // POST: Створити продукт
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product entity = productMapper.toEntity(productDTO);
        Product savedEntity = productService.save(entity);
        return new ResponseEntity<>(productMapper.toDto(savedEntity), HttpStatus.CREATED);
    }

    // GET: Отримати продукт за ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return productService.findById(id)
                .map(productMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Видалити продукт
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}