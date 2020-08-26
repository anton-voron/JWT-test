package com.voron.jwtapp.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.voron.jwtapp.details.CustomUserDetails;
import com.voron.jwtapp.details.CustomUserDetailsService;


import static org.springframework.util.StringUtils.hasText;

@Component
public class JwtFilter extends GenericFilterBean {
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
    public static final String AUTHORIZATION = "Authorization";


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String token = getToken((HttpServletRequest) request); 
		System.out.println("\n========>>>>> now in JwtFilter.doFilter(), we get token: " + token);
		if(token != null && jwtProvider.validateToken(token)) {
			System.out.println("Is our token valid: " + jwtProvider.validateToken(token));

			String username = jwtProvider.getUsernameFormToken(token);
			System.out.println("We get username form token: " + username);
			
			CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(username);
			System.out.println("We get CustomUserDetails form username: " + customUserDetails);
			
			// Here I have error!!!!!
			UsernamePasswordAuthenticationToken auth= new UsernamePasswordAuthenticationToken(customUserDetails, "", customUserDetails.getAuthorities());
			
			System.out.println("Here my auth result: " + auth);
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		chain.doFilter(request, response);
	}
	
	public String getToken(HttpServletRequest http) {
		String bearer = http.getHeader(AUTHORIZATION);
		if(hasText(bearer) && bearer.startsWith("Bearer ")) {
			return bearer.substring(7);
		}
		return null;
	}

}
