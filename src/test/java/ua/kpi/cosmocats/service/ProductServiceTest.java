package ua.kpi.cosmocats.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.kpi.cosmocats.client.ExchangeRateClient;
import ua.kpi.cosmocats.domain.Product;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ExchangeRateClient exchangeRateClient;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldSaveProduct_WithCalculatedCredits() {
        // Given
        Product product = new Product();
        product.setPrice(new BigDecimal("10.0"));

        // Навчаємо мок: коли спитають курс, поверни 25.0
        when(exchangeRateClient.getExchangeRate()).thenReturn(new BigDecimal("25.0"));

        // When
        Product savedProduct = productService.save(product);

        // Then
        assertNotNull(savedProduct.getId());
        // Перевіряємо математику: 10 * 25 = 250
        assertEquals(new BigDecimal("250.00"), savedProduct.getPriceInCredits());

        // Перевіряємо, що клієнт дійсно викликався
        verify(exchangeRateClient, times(1)).getExchangeRate();
    }

    @Test
    void shouldFindById() {
        // Given
        Product product = new Product();
        product.setId(1L);

        // Мокаємо курс, бо save() його викликає
        when(exchangeRateClient.getExchangeRate()).thenReturn(BigDecimal.ONE);
        productService.save(product);

        // When
        Optional<Product> found = productService.findById(1L);

        // Then
        assertTrue(found.isPresent());
        assertEquals(1L, found.get().getId());
    }
}