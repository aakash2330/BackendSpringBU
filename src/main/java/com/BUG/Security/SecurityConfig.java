package com.BUG.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.BUG.JWT.JWT_Filter;
import com.BUG.Services.ConvertUserEntityToCustomUser;

@Configuration
@EnableWebSecurity
@Component
@EnableGlobalMethodSecurity(
  securedEnabled = true,
  jsr250Enabled = true,
  prePostEnabled = true
)
public class SecurityConfig {
	
	@Autowired
	JWT_Filter jwtFilter;
	
	
		
					@Bean
					BCryptPasswordEncoder passwordEncoder() {
						return new BCryptPasswordEncoder();
						}

					@Bean
					UserDetailsService userDetailsService() {
						return new ConvertUserEntityToCustomUser();
					}

					@Bean
					DaoAuthenticationProvider authenticationProvider() {
						DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
						provider.setUserDetailsService(userDetailsService());
						provider.setPasswordEncoder(passwordEncoder());
						return provider;
					}
					
					@Bean
					SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
						httpSecurity.cors().disable()
						.csrf().disable().authorizeHttpRequests()
						.antMatchers("api/users/add").permitAll()
						.antMatchers("/api/users/*").permitAll()
						.antMatchers("/api/token/check").permitAll()
						.antMatchers("/api/token").permitAll()
						.antMatchers("/api/event/**").permitAll()
						.antMatchers("/api/events/**").permitAll()
						.antMatchers("/api/**").permitAll()
						
						.anyRequest()
						.authenticated()
						.and()
						.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
					
						
						httpSecurity.authenticationProvider(authenticationProvider());
						httpSecurity.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
						return httpSecurity.build();
					
					}



















}
