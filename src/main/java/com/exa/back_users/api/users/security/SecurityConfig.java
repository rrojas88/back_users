
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
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity( prePostEnabled = true )
public class SecurityConfig {

    @Bean
	PasswordEncoder passwordEncoder (){
		return new BCryptPasswordEncoder( );
	}
    /*
     * Credenciales "Basic Auth" para el URI "/actuator"
     */
    @Bean
	@Order(2)
	SecurityFilterChain actuatorFilterChain ( HttpSecurity http ) throws Exception
	{
		http
			.csrf().disable()
			.authorizeRequests()
			//.antMatchers("/actuator/**").hasAuthority("ACTUATOR")
			.antMatchers("/actuator/**").hasRole("ACTUATOR")
			.anyRequest().authenticated()
			.and().httpBasic()
			.and()
			.sessionManagement()
			.sessionCreationPolicy( SessionCreationPolicy.STATELESS );
        http.authenticationProvider(actuatorAuthProvider());
        return http.build();
	}  /*   */
    /*
     * Credenciales "Basic Auth" para todas las demás URIs (excluyendo "/actuator")
     */
    @Bean
	@Order(1)
	SecurityFilterChain filterChain ( HttpSecurity http ) throws Exception
	{
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/**").permitAll()
			.anyRequest().authenticated()
			.and().httpBasic()
			.and()
			.sessionManagement()
			.sessionCreationPolicy( SessionCreationPolicy.STATELESS );
        http.authenticationProvider(userAuthProvider());
        return http.build();
	}
	/*     */
	

	/* 
	@Bean
    @Order(1)
    public SecurityFilterChain actuatorWebSecurity(HttpSecurity http) throws Exception {
        http.requestMatchers((matchers) -> matchers
			.antMatchers("/actuator/**"))
			.authorizeRequests((requests) -> requests.anyRequest().authenticated())
			.httpBasic() ;
			//.and().formLogin();
		http.authenticationProvider(actuatorAuthProvider());
        return http.build();
    }

    @Bean
	@Order(2)
    public SecurityFilterChain webSecurity(HttpSecurity http) throws Exception {
        http.requestMatchers((matchers) -> matchers
			.antMatchers("/**"))
			.authorizeRequests(
				(requests) -> requests.anyRequest().authenticated()
			)
			.httpBasic()
			//.and().formLogin()
			.and()
			.exceptionHandling( exception -> { 				
				System.out.println(".................................................. Errror: "); 
				System.out.println( exception );
			}
			);
			//.authenticationEntryPoint( userAuthenticationErrorHandler() ) ;
			//.accessDeniedHandler(new UserForbiddenErrorHandler()));

		http.authenticationProvider(userAuthProvider());
        return http.build();
    }
	*/


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
	//@Primary
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
