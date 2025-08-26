package io.samwells.user_service.security;

import org.springframework.web.filter.OncePerRequestFilter;

import io.samwells.user_service.utils.JWTUtils;
import io.samwells.user_service.service.ExtendedUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JWTUtils jwtUtils;
    private final ExtendedUserService extendedUserService;
    
    public JwtAuthenticationFilter(JWTUtils jwtUtils, ExtendedUserService extendedUserService) {
        this.jwtUtils = jwtUtils;
        this.extendedUserService = extendedUserService;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = token.substring(7);
            
            var claims = jwtUtils.validateToken(jwt);

            Long userId = Long.valueOf(claims.getSubject());
            var user = extendedUserService.getUserById(userId);
            if (user == null) throw new UsernameNotFoundException("User not found");
        
            var authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        }
    }
}
