package ua.kpi.cosmocats.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@Component
public class ExchangeRateClient {

    private final RestClient restClient;

    public ExchangeRateClient(RestClient.Builder builder) {
        // Спробуй 127.0.0.1, це часто надійніше ніж localhost на Windows
        this.restClient = builder.baseUrl("http://127.0.0.1:8081").build();
    }

    public BigDecimal getExchangeRate() {
        System.out.println(">>> ПОЧАТОК ЗАПИТУ ДО WIREMOCK...");
        try {
            var response = restClient.get()
                    .uri("/api/rates")
                    .retrieve()
                    .body(RateResponse.class);

            if (response != null) {
                System.out.println(">>> УСПІХ! Отримано курс: " + response.rate());
                return response.rate();
            } else {
                System.out.println(">>> ВІДПОВІДЬ ПУСТА (NULL)");
                return null;
            }

        } catch (Exception e) {
            System.out.println(">>> ПОМИЛКА ПІДКЛЮЧЕННЯ: " + e.getMessage());
            e.printStackTrace(); // Це покаже повний стек помилки
            return null;
        }
    }

    record RateResponse(BigDecimal rate) {}
}