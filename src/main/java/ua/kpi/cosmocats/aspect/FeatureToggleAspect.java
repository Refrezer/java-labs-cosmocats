package ua.kpi.cosmocats.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ua.kpi.cosmocats.exception.FeatureNotAvailableException;
import ua.kpi.cosmocats.service.FeatureToggleService;

@Aspect
@Component
@RequiredArgsConstructor
public class FeatureToggleAspect {

    private final FeatureToggleService featureToggleService;

    @Around("@annotation(featureToggle)")
    public Object checkFeatureToggle(ProceedingJoinPoint joinPoint, FeatureToggle featureToggle) throws Throwable {
        // 1. Узнаем имя фичи из аннотации
        String featureName = featureToggle.value();

        // 2. Проверяем в сервисе, включена ли она
        if (!featureToggleService.check(featureName)) {
            // 3. Если выключена — кидаем ошибку
            throw new FeatureNotAvailableException("Feature '" + featureName + "' is disabled!");
        }

        // 4. Если включена — разрешаем методу выполняться
        return joinPoint.proceed();
    }
}