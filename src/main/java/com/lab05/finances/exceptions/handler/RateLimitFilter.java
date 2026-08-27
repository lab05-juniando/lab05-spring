package com.lab05.finances.exceptions.handler;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private static final int MAX_REQUESTS = 60;
    private static final long WINDOW_MILLIS = 60_000L;

    private final ConcurrentHashMap<String, RequestWindow> clients = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String clientKey = request.getRemoteAddr();
        long now = System.currentTimeMillis();
        RequestWindow window = clients.compute(clientKey, (key, current) -> {
            if (current == null || now - current.startedAt() >= WINDOW_MILLIS) {
                return new RequestWindow(now, new AtomicInteger(1));
            }
            current.requests().incrementAndGet();
            return current;
        });

        if (window.requests().get() > MAX_REQUESTS) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"status\":429,\"error\":\"Too Many Requests\",\"message\":\"Limite de requisicoes excedido. Tente novamente em alguns instantes.\",\"path\":\"" + request.getRequestURI() + "\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private record RequestWindow(long startedAt, AtomicInteger requests) {
    }
}