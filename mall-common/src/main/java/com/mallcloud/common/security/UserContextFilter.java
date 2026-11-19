package com.mallcloud.common.security;

import jakarta.servlet.FilterChain; import jakarta.servlet.ServletException; import jakarta.servlet.http.HttpServletRequest; import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component; import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class UserContextFilter extends OncePerRequestFilter {
    @Override protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String id = request.getHeader("X-User-Id");
        if (id != null && !id.isBlank()) UserContext.set(new AuthenticatedUser(Long.valueOf(id), request.getHeader("X-Username"), request.getHeader("X-User-Role")));
        try { chain.doFilter(request, response); } finally { UserContext.clear(); }
    }
}
