package ua.kpi.cosmocats;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ua.kpi.cosmocats.entity.Category;
import ua.kpi.cosmocats.entity.Order;
import ua.kpi.cosmocats.entity.Product;
import ua.kpi.cosmocats.service.CategoryService;
import ua.kpi.cosmocats.service.OrderService;
import ua.kpi.cosmocats.service.ProductService;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DemoRun implements CommandLineRunner {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final OrderService orderService;

    @Override
    public void run(String... args) throws Exception {
        // !!! ЛАЙФХАК ДЛЯ ЛР4 !!!
        // Встановлюємо "фейкову" аутентифікацію для DemoRun,
        // щоб Security пропустив нас у метод createProduct
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        "SYSTEM",
                        null,
                        AuthorityUtils.createAuthorityList("ROLE_API_USER") // Даємо собі потрібну роль
                )
        );

        System.out.println("----------- ПОЧАТОК ПЕРЕВІРКИ -----------");

        // 1. Створюємо категорію
        Category electronics = categoryService.createCategory("Електроніка", "Гаджети та інше");
        System.out.println("✅ Категорія створена: " + electronics.getName());

        // 2. Створюємо продукт (Тепер це спрацює, бо ми "авторизовані")
        Product phone = productService.createProduct("iPhone 3000", BigDecimal.valueOf(999.99), electronics.getId());
        System.out.println("✅ Продукт створено: " + phone.getName() + " (Ціна: " + phone.getPrice() + ")");

        // 3. Створюємо замовлення
        Order order = orderService.createOrder("test@kpi.ua");
        System.out.println("✅ Замовлення створено! Номер: " + order.getOrderNumber());

        // 4. Перевіряємо звіт
        System.out.println("📊 Звіт для комітету:");
        productService.getCorporateReport().forEach(p ->
                System.out.println(" - Товар: " + p.getName() + " | Категория: " + p.getCategoryName())
        );

        System.out.println("----------- ПЕРЕВІРКА УСПІШНА -----------");

        // Очищаємо за собою (хороший тон)
        SecurityContextHolder.clearContext();
    }
}