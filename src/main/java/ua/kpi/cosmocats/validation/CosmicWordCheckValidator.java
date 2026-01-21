package ua.kpi.cosmocats.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class CosmicWordCheckValidator implements ConstraintValidator<CosmicWordCheck, String> {

    // Список обов'язкових слів. Можна розширити.
    private static final List<String> COSMIC_TERMS = List.of("star", "galaxy", "comet", "space", "moon", "cosmo", "planet");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true; // @NotNull перевірить null, тут пропускаємо
        }
        String lowerCaseValue = value.toLowerCase();
        // Перевіряємо, чи є хоча б одне слово зі списку в назві
        return COSMIC_TERMS.stream().anyMatch(lowerCaseValue::contains);
    }
}