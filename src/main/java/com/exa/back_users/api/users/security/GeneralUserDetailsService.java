
/*    
package com.exa.back_users.api.users.security;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class GeneralUserDetailsService implements UserDetailsService  {


/* 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("-....... usuario = " + username );
        if ("user1".equals(username)) {

            return User.builder()
                    .username("user1")
                    .password("$2a$10$5AF5OW5b5f4ry4GQ2wemnObJbjtZrkBAA9pS7kxvqTxnMkjyfAIre") // Password: password
                    .roles("USER")
                    .build();
        }
        else {
            System.out.println("-....... NO usuario = " + username );
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }
    }
*/

/* Seguir acaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
    private Map<String, String> userMap = new HashMap<>();

    public GeneralUserDetailsService() {
        // Agregamos los usuarios "user1" y "user2" con contraseña "password"
        userMap.put("user1", "$2a$10$3qT6TgMXZz2M6Nry7HrBQ.XnL7VJxHxGvKvpYfsoIYWoZVnHPmJjK");
        //userMap.put("user2", "$2a$10$3qT6TgMXZz2M6Nry7HrBQ.XnL7VJxHxGvKvpYfsoIYWoZVnHPmJjK");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("-....... usuario = " + username );
        String password = userMap.get(username);
        System.out.println( password );
        if (password == null) {
            String message = "User not found";
            System.out.println( message );
            throw new UsernameNotFoundException( message );
        }
        System.out.println( "-....... Pasaaaa ok" );
        //List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        System.out.println( authorities );
        UserDetails user = new User(username, password, authorities);
        System.out.println( user );
        return user;
    }
    
}

*/
