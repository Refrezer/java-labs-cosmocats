package ua.kpi.cosmocats.repository;

import java.math.BigDecimal;

public interface ProductReportProjection {
    String getName();
    BigDecimal getPrice();
    String getCategoryName(); // Spring умный, он вытащит это из связанной категории
}