package ua.kpi.cosmocats.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CosmicWordCheckValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CosmicWordCheck {
    String message() default "Назва продукту має містити космічні терміни (star, galaxy, comet, space, moon)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}