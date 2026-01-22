We will implement a Many-to-Many relationship. This is the industry standard because a single User (e.g., "Sahil") might need multiple Roles (e.g., both "ROLE_USER" and "ROLE_MANAGER").

**1. Database Schema Changes (SQL)**

First, we need to create the ```roles``` table and a "junction" table to link users and roles. Run this SQL in your database:

```sql```
```
-- 1. Create the Roles table
CREATE TABLE roles (
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

-- 2. Create the Junction Table (Links Users to Roles)
CREATE TABLE users_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

-- 3. Pre-populate Roles (Spring Security expects 'ROLE_' prefix)
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
```

**2. Create the Role Entity (```Role.java```)**
Create this new class in your ```entity``` package to map the new table.

```Java```
```
package com.ignou.lab.entity;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int id;

    private String name;

    // Constructors
    public Role() {}
    public Role(String name) { this.name = name; }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
```

**3. Update User Entity (```User.java```)**
We must replace the old ```String role``` field with a collection of Role objects.

Key Change: We use ```FetchType.EAGER```. When Spring Security loads a User, it must load the roles immediately to authorize requests; otherwise, you may get a "LazyInitializationException".

```Java```
```
package com.ignou.lab.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;
    private boolean enabled = true;

    // --- NEW: Many-to-Many Relationship ---
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles = new HashSet<>();

    // Helper method to add a role easily
    public void addRole(Role role) {
        this.roles.add(role);
    }

    // Getters and Setters
    public Collection<Role> getRoles() { return roles; }
    public void setRoles(Collection<Role> roles) { this.roles = roles; }
    
    // ... keep get/setUsername, get/setPassword, isEnabled ...
    // Make sure to remove the old 'String role' field and its getters/setters!
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
}

```


**4. Create Role Repository (```RoleRepository.java```)**

We need this to fetch the "ROLE_USER" object from the database when a new user registers.

```Java```
```
package com.ignou.lab.repository;

import com.ignou.lab.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}

```

**5. Update CustomUserDetailsService**

We need to update the logic that converts our Database User into a Spring Security User. It now needs to loop through the list of roles instead of just reading a single string.

```Java```
```
package com.ignou.lab.service;

import com.ignou.lab.entity.Role;
import com.ignou.lab.entity.User;
import com.ignou.lab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true, true, true,
                mapRolesToAuthorities(user.getRoles()) // Helper method call
        );
    }

    // Convert List<Role> -> List<GrantedAuthority>
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
```

**6. Update Registration Logic (```UserService.java```)**

Finally, update the registration service to assign the Role entity instead of a string.

```Java```
```
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public void registerAndAutoLogin(UserRegistrationDto userDto, HttpServletRequest request) {
        User newUser = new User();
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(userDto.getPassword()); 
        newUser.setEnabled(true);

        // Fetch the Role from DB (default to ROLE_USER)
        Role role = roleRepository.findByName("ROLE_" + userDto.getRole());
        
        // Safety check: Create role if it doesn't exist yet
        if(role == null) {
            role = new Role("ROLE_" + userDto.getRole());
            roleRepository.save(role);
        }
        
        newUser.addRole(role);

        userRepository.save(newUser);
        
        // ... (Auto-login logic remains similar, just pass authorities) ...
    }
```
The database and application are now configured for Role-Based Authentication. I can now assign multiple roles to users and scale your permission system easily.












