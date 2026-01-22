package ua.kpi.cosmocats.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // Вешается только на методы
@Retention(RetentionPolicy.RUNTIME) // Работает во время выполнения
public @interface FeatureToggle {
    String value(); // Имя фичи (например "cosmo-cats")
}