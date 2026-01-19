we will move away from the default "admin" user and connect Spring Security to your MySQL database. This allows users stored in your database to log in.
We need four components to "bind" your database entity to Spring Security:
A User Entity (The Database Table).
A Repository (To fetch the user).
A Custom UserDetailsService (The logic to bridge Database User → Spring Security User).
A Security Configuration (To wire everything together).
1. The User Entity (User.java)
We create a standard entity to store credentials. We implement UserDetails is not strictly necessary if we use a mapping service, but storing roles as a simple string works for this stage.
File: src/main/java/com/ignou/lab/entity/User.java

```
package com.ignou.lab.entity;

import javax.persistence.*;

@Entity
@Table(name = "users") // 'User' is a reserved keyword in some SQL DBs
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String role; // e.g., "ROLE_USER", "ROLE_ADMIN"
    private boolean enabled = true;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
}

```
2. The User Repository (UserRepository.java)
We need a method to find a user by their username.
File: src/main/java/com/ignou/lab/repository/UserRepository.java


```
package com.ignou.lab.repository;

import com.ignou.lab.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}

```

3. The Custom Service (CustomUserDetailsService.java)
This is the Binding Layer. Spring Security calls loadUserByUsername, and this service fetches your entity and converts it into a Spring Security-compatible UserDetails object.
File: src/main/java/com/ignou/lab/service/CustomUserDetailsService.java

```
package com.ignou.lab.service;

import com.ignou.lab.entity.User;
import com.ignou.lab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Fetch User from DB
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // 2. Return Spring Security User object (The "Principal")
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // In real apps, this should be encoded (BCrypt)
                .roles(user.getRole().replace("ROLE_", "")) // Spring auto-adds "ROLE_"
                .disabled(!user.isEnabled())
                .build();
    }
}

```

4. Security Configuration (SecurityConfig.java)
Finally, we tell Spring Security to use our custom service instead of the default "user/password" setup.
File: src/main/java/com/ignou/lab/config/SecurityConfig.java

```
package com.ignou.lab.config;

import com.ignou.lab.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // 1. Password Encoder (Using NoOp for this lab exercise; use BCrypt for production)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // 2. Authentication Provider (Connects Service + Encoder)
    @Bean
    public DaoAuthenticationProvider authenticationProvider(CustomUserDetailsService userDetailsService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService); // Use our DB logic
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    // 3. Security Filter Chain (Define URL Rules)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(auth -> auth
                .antMatchers("/login", "/resources/**").permitAll() // Allow public access
                .anyRequest().authenticated() // Secure everything else
            )
            .formLogin(form -> form
                .permitAll() // Enable default login page
            )
            .logout(logout -> logout.permitAll());
            
        // Fix for H2 console or POST requests if CSRF is an issue in labs
        http.csrf().disable(); 
        
        return http.build();
    }
}

```

How to Test:
Insert a User into the Database:
Since we don't have a registration page yet, run this SQL in your database tool:

```
Insert a User into the Database:
Since we don't have a registration page yet, run this SQL in your database tool:
```

Run the App:
Go to http://localhost:8080.

Login:
Use sahil / sahil123.