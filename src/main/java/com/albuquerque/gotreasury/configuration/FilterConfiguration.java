package com.albuquerque.gotreasury.configuration;

import com.albuquerque.gotreasury.dto.ErrorDTO;
import com.albuquerque.gotreasury.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;

@Component
@AllArgsConstructor
public class FilterConfiguration extends OncePerRequestFilter {

    private final JwtService auth;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var token = request.getHeader("Authorization");
        try {
            if (token != null) {
                var decodedJWT = auth.decode(token.replace("Bearer ", ""));
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), decodedJWT.getClaim("password"), Collections.emptyList()));
            }
        } catch (Exception e) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            var error = new ErrorDTO(Instant.now().toString(), 401, "Unauthorized", e.getMessage(), request.getRequestURI());
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), error);
            return;
        }

        filterChain.doFilter(request, response);
    }
}