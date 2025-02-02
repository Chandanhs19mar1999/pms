package com.elva.pms.filter;

import com.elva.pms.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        String userType = "USER";

        String jwtToken = JwtUtil.extractJwtFromRequest(request);
        String xAccessToken = request.getHeader("x-access-token");

        if (StringUtils.hasText(jwtToken)) {
            authenticateOwner(jwtToken);
        } else if (StringUtils.hasText(xAccessToken)) {
            authenticateUser(xAccessToken);
        }
        filterChain.doFilter(request, response);
    }

    private void authenticateOwner(String jwtToken) {
        try {
            Claims claims = JwtUtil.getClaimsFromJwt(jwtToken);
            String username = claims.getSubject();
            logger.info("authenticated username: " + username);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    new User("OWNER", "", Collections.emptyList()), null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e) {
            throw new BadCredentialsException("INVALID BEARER TOKEN");
        }
    }

    private void authenticateUser(String xAccessToken) {
        if ("123".equals(xAccessToken)) {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    "USER", null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            throw new RuntimeException("INVALID TOKEN");
        }
    }
}
