package ua.kpi.cosmocats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize; // ⚠️ Новий імпорт для безпеки
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

    // 🔥 ДОДАНО БЕЗПЕКУ:
    // Тільки юзер з роллю API_USER (наш API Key) АБО токеном з правами 'write' може викликати цей метод.
    // Якщо прав немає — буде помилка 403 Forbidden.
    @PreAuthorize("hasAuthority('ROLE_API_USER') or hasAuthority('SCOPE_write')")
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

    // Інші методи залишаємо відкритими (або теж можна захистити, якщо треба)

    // Використання кастомного запиту на "дешеві товари"
    public List<Product> findCheapProducts(BigDecimal maxPrice) {
        return productRepository.findCheapProducts(maxPrice);
    }

    // Той самий метод для "Інтергалактичного комітету" (Projection)
    public List<ProductReportProjection> getCorporateReport() {
        return productRepository.getTopExpensiveProducts();
    }
}