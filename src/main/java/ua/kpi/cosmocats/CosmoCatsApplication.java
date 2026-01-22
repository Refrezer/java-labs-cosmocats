package ua.kpi.cosmocats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties; // <--- Импорт
import ua.kpi.cosmocats.service.FeatureToggleService;

@SpringBootApplication
@EnableConfigurationProperties(FeatureToggleService.class) // <--- ОБЯЗАТЕЛЬНО ДОБАВЬ ЭТО
public class CosmoCatsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CosmoCatsApplication.class, args);
    }
}