package ua.kpi.cosmocats.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import ua.kpi.cosmocats.validation.CosmicWordCheck;
import java.math.BigDecimal;

@Data
public class ProductDTO {

    private Long id;

    @NotNull(message = "Назва продукту не може бути порожньою")
    @Size(min = 3, max = 100, message = "Назва має бути від 3 до 100 символів")
    @CosmicWordCheck
    private String name;

    @NotNull(message = "Ціна обов'язкова")
    @DecimalMin(value = "0.01", message = "Ціна має бути більшою за 0")
    private BigDecimal price; // Ціна в доларах

    private BigDecimal priceInCredits; // Нове поле: Ціна в Галактичних Кредитах

    private String description;
}