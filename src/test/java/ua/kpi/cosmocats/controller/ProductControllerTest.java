package ua.kpi.cosmocats.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ua.kpi.cosmocats.domain.Product;
import ua.kpi.cosmocats.dto.ProductDTO;
import ua.kpi.cosmocats.mapper.ProductMapper;
import ua.kpi.cosmocats.service.ProductService;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductMapper productMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn201_WhenProductIsValid() throws Exception {
        // Given
        ProductDTO validDto = new ProductDTO();
        validDto.setName("Super Star Milk");
        validDto.setPrice(new BigDecimal("10.0"));

        Product mockProduct = new Product(1L, "Super Star Milk", new BigDecimal("10.0"), null, null);

        // Мокаємо залежності, щоб контролер не звертався до реальної логіки
        when(productMapper.toEntity(any())).thenReturn(mockProduct);
        when(productService.save(any())).thenReturn(mockProduct);
        when(productMapper.toDto(any())).thenReturn(validDto);

        // When & Then
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Super Star Milk"));
    }

    @Test
    void shouldReturn400_WhenNameInvalid() throws Exception {
        // Given
        ProductDTO invalidDto = new ProductDTO();
        invalidDto.setName("Just Milk"); // Помилка: немає космічного слова
        invalidDto.setPrice(new BigDecimal("10.0"));

        // When & Then
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest()) // Очікуємо 400
                .andExpect(jsonPath("$.error").value("Bad Request"));
    }
}