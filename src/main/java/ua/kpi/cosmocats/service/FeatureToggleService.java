package ua.kpi.cosmocats.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@Data
@ConfigurationProperties(prefix = "feature") // Связывает с yaml
public class FeatureToggleService {

    // Сюда Spring сам зальет значения из yaml
    private Map<String, Boolean> toggles;

    // Метод проверки: если флага нет, считаем, что false
    public boolean check(String featureName) {
        return toggles != null && toggles.getOrDefault(featureName, false);
    }
}