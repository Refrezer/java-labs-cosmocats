package ua.kpi.cosmocats.mapper;

import org.mapstruct.Mapper;
import ua.kpi.cosmocats.domain.Product;
import ua.kpi.cosmocats.dto.ProductDTO;

@Mapper(componentModel = "spring") // Це робить маппер Spring біном
public interface ProductMapper {
    ProductDTO toDto(Product product);
    Product toEntity(ProductDTO productDTO);
}