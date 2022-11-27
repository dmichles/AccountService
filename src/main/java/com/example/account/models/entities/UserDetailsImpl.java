package com.example.account.models.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private String username;
    private String password;
    private boolean isAccountNonLocked;
    Collection<GrantedAuthority> authorityList;

    public UserDetailsImpl(User user){
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.isAccountNonLocked = user.isAccountNonLocked();
        //authorityList = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        authorityList = authorities(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return  isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Collection<GrantedAuthority> authorities(User user){
        Collection<GrantedAuthority> list = new ArrayList<>();
        user.getUserGroups().forEach(group -> list.add(new SimpleGrantedAuthority(group.getRole())));
        return list;
    }
}
