package com.BUG.JWT;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.BUG.Services.ConvertUserEntityToCustomUser;

@Component
public class JWT_Filter extends OncePerRequestFilter {


	@Autowired
	JWT_Util jwtUtil;
	
	@Autowired
	ConvertUserEntityToCustomUser userDetailsService;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
			System.out.println(request.getHeader("authorization"));
		
		String RequestHeader = request.getHeader("authorization");
		String username;
		String JWTtoken;
		System.out.println("JWT_TOKEN"+"----" +RequestHeader);
		
		if(RequestHeader!=null && RequestHeader.startsWith("Bearer ")) {
			JWTtoken = RequestHeader.substring(7);
			
			try {
				username = this.jwtUtil.extractUsername(JWTtoken);
				System.out.println("USERNAME"+username);
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		else {
			System.out.println("NOT VALIDATED");
		}
		
		filterChain.doFilter(request, response);
		
	}

}

