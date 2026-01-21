package ua.kpi.cosmocats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.cosmocats.client.ExchangeRateClient;
import ua.kpi.cosmocats.domain.Product;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final Map<Long, Product> mockDb = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    private final ExchangeRateClient exchangeRateClient;

    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(idGenerator.getAndIncrement());
        }

        System.out.println(">>> СЕРВІС: Починаємо збереження продукту...");

        // Викликаємо клієнт
        BigDecimal rate = exchangeRateClient.getExchangeRate();
        System.out.println(">>> СЕРВІС: Курс отриманий від клієнта: " + rate);

        if (product.getPrice() != null && rate != null) {
            BigDecimal credits = product.getPrice().multiply(rate);
            product.setPriceInCredits(credits);
            System.out.println(">>> СЕРВІС: Перерахована ціна: " + credits);
        } else {
            System.out.println(">>> СЕРВІС: Перерахунок не вдався (ціна або курс null)");
        }

        mockDb.put(product.getId(), product);
        return product;
    }

    // ... інші методи (findAll і т.д.) залиш як були
    public List<Product> findAll() { return new ArrayList<>(mockDb.values()); }
    public Optional<Product> findById(Long id) { return Optional.ofNullable(mockDb.get(id)); }
    public boolean deleteById(Long id) { return mockDb.remove(id) != null; }
}