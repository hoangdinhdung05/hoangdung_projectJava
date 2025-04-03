package vn.hoangdung.projectJava.helpers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import vn.hoangdung.projectJava.modules.users.services.impl.CustomUserDetailsService;
import vn.hoangdung.projectJava.modules.users.services.impl.UserService;
import vn.hoangdung.projectJava.services.JwtService;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Override
    public void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {


        //Lấy token từ header
        final String authHeader = request.getHeader("Authorization");
        final String userId;
        final String jwt;

        //check token có tồn tại không || null
        if(authHeader == null || !authHeader.startsWith("Bearer")) {

            logger.error("Token miss");

            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        userId = this.jwtService.getUserIdFromJwt(jwt);

        if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(userId);
            logger.info(userDetails.getUsername());
        }

        filterChain.doFilter(request, response);


    }

}
