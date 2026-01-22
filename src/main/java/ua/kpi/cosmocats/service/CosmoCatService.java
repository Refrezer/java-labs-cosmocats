package ua.kpi.cosmocats.service;

import org.springframework.stereotype.Service;
import ua.kpi.cosmocats.aspect.FeatureToggle;
import java.util.List;

@Service
public class CosmoCatService {

    @FeatureToggle("cosmo-cats") // Включено в yaml
    public List<String> getCosmoCats() {
        return List.of("Barsik", "Murzik", "SpaceX-Cat");
    }

    @FeatureToggle("kitty-products") // Выключено в yaml
    public String getSecretProduct() {
        return "Radioactive Catnip";
    }
}