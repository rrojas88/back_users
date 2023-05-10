
package com.exa.back_users.api.users.security;


import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
	PasswordEncoder passwordEncoder (){
		return new BCryptPasswordEncoder( );
	}

	/*
     * Credenciales "Basic Auth" para el URI "/actuator"
     */
    @Bean
	@Order( 1 )
	SecurityFilterChain actuatorFilterChain ( HttpSecurity http ) throws Exception
	{
		http
			.antMatcher("/actuator/**")
			.authorizeHttpRequests()
			.antMatchers("/actuator/**").hasRole("ACTUATOR")
			.and()
			.httpBasic();
        return http.build();
	}
	
	/*
     * Credenciales "Basic Auth" para todas las demás URIs (excluyendo "/actuator")
     */
    @Bean
	SecurityFilterChain filterChain ( HttpSecurity http ) throws Exception
	{
		http 
			.authorizeRequests()
            .antMatchers("/actuator/**").permitAll()
            .antMatchers("/**").hasRole("USER")
	        .and()
	        .httpBasic();
        return http.build();
	}

    /* 
    * Crendenciales en "Memoria" de los 2 "Usuarios"
    */
	@Bean
	UserDetailsService userDetailsService()
	{
		InMemoryUserDetailsManager umanager = new InMemoryUserDetailsManager();
		umanager.createUser( User.withUsername("admin")
			.password( passwordEncoder().encode("admin") )
			.roles("USER")
			.build() 
		);
		umanager.createUser( User.withUsername("actuator")
			.password( passwordEncoder().encode("actuator") )
			.roles("ACTUATOR")
			.build() 
		);
		return umanager;
	}
    
}
