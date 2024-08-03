package com.app.test.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.app.test.entity.Authority;
import com.app.test.entity.Users;
import com.app.test.roles.Roles;

public class CustomUserDetails implements UserDetails {

    private final Users users;

    public CustomUserDetails(final Users users) {
        this.users = users;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (Authority roles: users.getAuthority()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(roles.getRoles().name());
            authorities.add(grantedAuthority);
        }

        return authorities; 
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getUsername();
    }
    
}
