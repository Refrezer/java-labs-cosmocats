package ua.kpi.cosmocats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.kpi.cosmocats.entity.Order;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Поиск по Natural ID (номеру заказа)
    Optional<Order> findByOrderNumber(String orderNumber);

    // Поиск всех заказов конкретного email
    @Query("SELECT o FROM Order o WHERE o.customerEmail = :email")
    List<Order> findAllByCustomerEmail(@Param("email") String email);
}