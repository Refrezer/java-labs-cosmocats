package ua.kpi.cosmocats.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    private static final String HEADER_NAME = "x-api-key";
    private static final String API_KEY = "cosmo-secret-123"; // В реальності це має бути в properties

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Отримуємо ключ із заголовка
        String requestApiKey = request.getHeader(HEADER_NAME);

        // Якщо ключ валідний — аутентифікуємо користувача як "API_USER"
        if (API_KEY.equals(requestApiKey)) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    "API_USER",
                    null,
                    AuthorityUtils.createAuthorityList("ROLE_API_USER", "SCOPE_read", "SCOPE_write")
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Пропускаємо запит далі по ланцюжку (навіть якщо ключа немає, SecurityConfig вирішить, що робити)
        filterChain.doFilter(request, response);
    }
}