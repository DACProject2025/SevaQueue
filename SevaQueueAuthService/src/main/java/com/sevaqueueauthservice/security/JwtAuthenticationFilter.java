	package com.sevaqueueauthservice.security;
	
	import java.io.IOException;
	
	import jakarta.servlet.FilterChain;
	import jakarta.servlet.ServletException;
	import jakarta.servlet.http.HttpServletRequest;
	import jakarta.servlet.http.HttpServletResponse;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
	import org.springframework.security.core.context.SecurityContextHolder;
	import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
	
	@Component
	public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	    @Autowired
	    private JwtUtils jwtUtils;
	
	    @Override
	    protected void doFilterInternal(
	            HttpServletRequest request,
	            HttpServletResponse response,
	            FilterChain filterChain)
	            throws ServletException, IOException {
	
	        String authHeader = request.getHeader("Authorization");
	
	        if (authHeader != null && authHeader.startsWith("Bearer ")) {
	
	            String token = authHeader.substring(7);
	
	            try {
	                String email = jwtUtils.getUsernameFromToken(token);
	
	                if (email != null &&
	                    SecurityContextHolder.getContext().getAuthentication() == null) {
	
	                    UserPrincipal userPrincipal = jwtUtils.getUserFromToken(token);
	
	                    UsernamePasswordAuthenticationToken authentication =
	                            new UsernamePasswordAuthenticationToken(
	                                    userPrincipal,
	                                    null,
	                                    userPrincipal.getAuthorities()
	                            );
	
	                    authentication.setDetails(
	                            new WebAuthenticationDetailsSource().buildDetails(request)
	                    );
	
	                    SecurityContextHolder.getContext().setAuthentication(authentication);
	                }
	
	            } catch (Exception e) {
	                SecurityContextHolder.clearContext();
	            }
	        }
	
	        filterChain.doFilter(request, response);
	    }
	}
	
