
package com.exa.back_users.api.users.security;

import java.util.*;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {


    @Bean
	PasswordEncoder passwordEncoder (){
		return new BCryptPasswordEncoder( );
	}


    /*
     * Credenciales "Basic Auth" para el URI "/actuator"
     */
    @Bean
	SecurityFilterChain actuatorFilterChain ( HttpSecurity http, AuthenticationManager actuatorAuthManager ) throws Exception
	{
		http
			.csrf().disable() // Inhabilitar cookies
			.authorizeRequests() // Reglas de Peticiones
			// Incluye "/actuator" de la autenticación básica
			//.antMatchers("/actuator/**").authenticated() hasAuthority("ACTUATOR")
			.antMatchers("/actuator/**").hasAuthority("ACTUATOR")
             // Para las demas debe estar autenticado
			.anyRequest().authenticated()
			.and().httpBasic() // Basic Auth
			.and()
			.sessionManagement() // Politica de Session SIN estado:
			.sessionCreationPolicy( SessionCreationPolicy.STATELESS );
			//.and().build();
        http.authenticationProvider(actuatorAuthProvider());
        return http.build();
	}

	
    /*
     * Credenciales "Basic Auth" para todas las demás URIs (excluyendo "/actuator")
     */
    @Bean
	SecurityFilterChain filterChain ( HttpSecurity http, AuthenticationManager userAuthManager ) throws Exception
	{
		http
			.csrf().disable() // Inhabilitar cookies
			.authorizeRequests() // Reglas de Peticiones
			// Excluye "/actuator" de la autenticación básica
			//.antMatchers("/actuator/**").permitAll()
			.antMatchers("/actuator/**").permitAll()
             // Para las demas debe estar autenticado
			.anyRequest().authenticated()
			.and().httpBasic() // Basic Auth
			.and()
			.sessionManagement() // Politica de Session SIN estado:
			.sessionCreationPolicy( SessionCreationPolicy.STATELESS );
			//.and().build();
        http.authenticationProvider(userAuthProvider());
        return http.build();
	}


    @Bean
    public DaoAuthenticationProvider userAuthProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService( userDetailsService() );
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    public DaoAuthenticationProvider actuatorAuthProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService( actuatorDetailsService() );
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
	@Primary
	public AuthenticationManager userAuthManager( HttpSecurity http ) throws Exception {
        return http.getSharedObject( AuthenticationManagerBuilder.class )
			.userDetailsService( userDetailsService() )
			.passwordEncoder( passwordEncoder() )
			.and()
			.build();
    }

    @Bean
	public AuthenticationManager actuatorAuthManager( HttpSecurity http ) throws Exception {
        return http.getSharedObject( AuthenticationManagerBuilder.class )
			.userDetailsService( actuatorDetailsService() )
			.passwordEncoder( passwordEncoder() )
			.and()
			.build();
    }



    /* 
    * Crendenciales en "Memoria" del usuario "admin"
    */
	@Bean
	UserDetailsService userDetailsService()
	{
		InMemoryUserDetailsManager umanager = new InMemoryUserDetailsManager();
		umanager.createUser( User.withUsername("admin")
			.password( passwordEncoder().encode("admin") )
			.roles()
			.build() 
		);
		return umanager;
	}


    /* 
    * Crendenciales en "Memoria" del usuario "actuator"
    */
	@Bean
	UserDetailsService actuatorDetailsService()
    {
		InMemoryUserDetailsManager umanager = new InMemoryUserDetailsManager();
		umanager.createUser( User.withUsername("actuator")
			.password( passwordEncoder().encode("actuator") )
			.roles("ACTUATOR")
			.build() 
		);
		return umanager;
	}


    

}
