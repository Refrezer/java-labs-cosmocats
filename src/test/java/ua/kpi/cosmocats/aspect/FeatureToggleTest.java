package ua.kpi.cosmocats.aspect;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.kpi.cosmocats.exception.FeatureNotAvailableException;
import ua.kpi.cosmocats.service.CosmoCatService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FeatureToggleTest {

    @Autowired
    private CosmoCatService cosmoCatService;

    @Test
    void shouldWork_WhenFeatureIsEnabled() {
        // Проверяем, что метод работает и возвращает список
        assertDoesNotThrow(() -> {
            var cats = cosmoCatService.getCosmoCats();
            assertFalse(cats.isEmpty());
            System.out.println("Cats loaded: " + cats);
        });
    }

    @Test
    void shouldThrow_WhenFeatureIsDisabled() {
        // Проверяем, что метод кидает ошибку
        assertThrows(FeatureNotAvailableException.class, () -> {
            cosmoCatService.getSecretProduct();
        });
    }
}