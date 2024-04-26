package com.example.wbcorps.ecom.filters;

import com.example.wbcorps.ecom.services.jwt.jwt.UserDetailsServiceImpl;
import com.example.wbcorps.ecom.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("JwtRequestFilter: Intercepting request");
        String authHeader = request.getHeader("Authorization");
        System.out.println("JwtRequestFilter: Authorization header: " + authHeader);
        String token = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            System.out.println("JwtRequestFilter: JWT token: " + token);
            username = jwtUtil.extractUsername(token);
            System.out.println("JwtRequestFilter: Extracted username: " + username);
        }
        if (username!=null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails= userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }else {
                System.out.println("JwtRequestFilter: Invalid token");
            }



        }
        filterChain.doFilter(request,response);
        System.out.println("JwtRequestFilter: Request processed. Continuing filter chain");
        return;
    }
}
