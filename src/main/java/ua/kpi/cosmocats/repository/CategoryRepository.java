package ua.kpi.cosmocats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kpi.cosmocats.entity.Category;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Найти категорию по названию
    Optional<Category> findByName(String name);
}