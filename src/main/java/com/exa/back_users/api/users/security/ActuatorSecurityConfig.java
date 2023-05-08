
/*  

package com.exa.back_users.api.users.security;



import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
//@Order( 1 )
@EnableWebSecurity
public class ActuatorSecurityConfig {

    @Bean
	SecurityFilterChain filterChainActuator ( HttpSecurity http, AuthenticationManager authManagerActuator ) throws Exception
	{
		return http
			.csrf().disable() // Inhabilitar cookies
			.authorizeRequests() // Reglas de Peticiones
            // Para Metricas debe estar Autenticado
            .antMatchers("/actuator/**").authenticated()
			.and().httpBasic() // Postman: Basic Auth
			.and().sessionManagement() // Politica de Session SIN estado:
			.sessionCreationPolicy( SessionCreationPolicy.STATELESS )
			.and()
			.build();
	}


    @Bean
	public AuthenticationManager authManagerActuator( HttpSecurity http ) throws Exception {
        return http.getSharedObject( AuthenticationManagerBuilder.class )
			.userDetailsService( userDetailsServiceActuator() )
			.passwordEncoder( passwordEncoderActuator() )
			.and()
			.build();
    }


    /* ========================================
    Usuario en "Memoria"
    */
    /*  
	@Bean
	UserDetailsService userDetailsServiceActuator()
	{
		// Usuario en "memoria"
		InMemoryUserDetailsManager umanager = new InMemoryUserDetailsManager();
		umanager.createUser( User.withUsername("prome")
			.password( passwordEncoderActuator().encode("theus") )
			.roles()
			.build() 
		);
		return umanager;
	}


    @Bean
	PasswordEncoder passwordEncoderActuator (){
		return new BCryptPasswordEncoder( );
	}
    
}

*/
