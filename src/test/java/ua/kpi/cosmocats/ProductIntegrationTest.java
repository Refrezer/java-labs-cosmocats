package ua.kpi.cosmocats;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.kpi.cosmocats.entity.Category;
import ua.kpi.cosmocats.entity.Product;
import ua.kpi.cosmocats.service.CategoryService;
import ua.kpi.cosmocats.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

// Ми прибрали @Testcontainers, тепер Spring візьме налаштування з application.yaml (H2)
@SpringBootTest
class ProductIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Test
    void shouldCreateAndFindProduct() {
        // Given (Підготовка): Створюємо категорію
        Category cat = categoryService.createCategory("Integration Test Electronics", "H2 Database Power");

        // When (Дія): Створюємо продукт
        productService.createProduct("Test Phone", BigDecimal.valueOf(500.00), cat.getId());

        // Then (Перевірка): Шукаємо "дешеві" продукти (до 600)
        List<Product> cheapProducts = productService.findCheapProducts(BigDecimal.valueOf(600.00));

        // Перевіряємо, що продукт знайшовся
        Assertions.assertFalse(cheapProducts.isEmpty(), "Список продуктів не має бути порожнім");

        // Перевіряємо, що це саме той продукт (фільтруємо по імені, бо в базі можуть бути дані з DemoRun)
        boolean productFound = cheapProducts.stream()
                .anyMatch(p -> p.getName().equals("Test Phone"));

        Assertions.assertTrue(productFound, "Мали знайти продукт 'Test Phone'");

        System.out.println("✅ ТЕСТ ПРОЙШОВ УСПІШНО! (Використано базу H2)");
    }
}