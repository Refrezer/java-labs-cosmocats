package ua.kpi.cosmocats;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ua.kpi.cosmocats.dto.ProductDTO;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Створюємо сервер WireMock на порту 8081
    private static WireMockServer wireMockServer = new WireMockServer(8081);

    @BeforeAll
    static void startWireMock() {
        wireMockServer.start();
        // Налаштовуємо заглушку: на запит /api/rates відповідати { "rate": 25.0 }
        wireMockServer.stubFor(WireMock.get(urlEqualTo("/api/rates"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"rate\": 25.0 }")));
    }

    @AfterAll
    static void stopWireMock() {
        wireMockServer.stop();
    }

    @Test
    void shouldCreateProductWithCreditPrice() throws Exception {
        // Given
        ProductDTO request = new ProductDTO();
        request.setName("Super Galaxy Milk");
        request.setPrice(new BigDecimal("10.00")); // 10 USD

        // When & Then
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Super Galaxy Milk"))
                // Перевірка: 10 * 25 = 250
                .andExpect(jsonPath("$.priceInCredits").value(250.0));
    }
}