package ua.kpi.cosmocats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.cosmocats.entity.Category;
import ua.kpi.cosmocats.entity.Product;
import ua.kpi.cosmocats.repository.CategoryRepository;
import ua.kpi.cosmocats.repository.ProductReportProjection;
import ua.kpi.cosmocats.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Product createProduct(String name, BigDecimal price, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setCategory(category);

        return productRepository.save(product);
    }

    // Использование кастомного запроса на "дешевые товары"
    public List<Product> findCheapProducts(BigDecimal maxPrice) {
        return productRepository.findCheapProducts(maxPrice);
    }

    // Тот самый метод для "Интергалактического комитета" (Projection)
    public List<ProductReportProjection> getCorporateReport() {
        return productRepository.getTopExpensiveProducts();
    }
}