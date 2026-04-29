package com.kiosko.authservice.security;

import com.kiosko.authservice.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));

            role.getPermissions().forEach(permission ->
                    authorities.add(new SimpleGrantedAuthority(permission.getFullPermission()))
            );
        });

        user.getPermissions().forEach(permission ->
                authorities.add(new SimpleGrantedAuthority(permission.getFullPermission()))
        );

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO: Re-enable for production
        return user.getActive();

        // PRODUCTION VERSION (uncomment when email verification is implemented):
        // return user.getActive() && user.getEmailVerified();
    }

    public User getUser() {
        return user;
    }
}