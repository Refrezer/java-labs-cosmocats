package ua.kpi.cosmocats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.kpi.cosmocats.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 1. Обычный поиск продуктов дороже определенной цены
    List<Product> findByPriceGreaterThan(BigDecimal price);

    // 2. Кастомный запрос JPQL (+1 балл)
    // Находим "дешевые" продукты
    @Query("SELECT p FROM Product p WHERE p.price < :maxPrice")
    List<Product> findCheapProducts(@Param("maxPrice") BigDecimal maxPrice);

    // 3. Запрос с Проекцией (+1 балл)
    // Возвращаем не весь объект, а только нужные поля (имя, цена, имя категории)
    @Query("SELECT p.name as name, p.price as price, c.name as categoryName " +
            "FROM Product p JOIN p.category c " +
            "ORDER BY p.price DESC")
    List<ProductReportProjection> getTopExpensiveProducts();
}