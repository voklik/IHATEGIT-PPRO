package com.PPROHORAK.Projekt.securingweb;


import com.PPROHORAK.Projekt.Model.Ucet;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private Ucet ucet;

    public UserPrincipal(Ucet ucet) {
        this.ucet = ucet;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority(this.ucet.getTypUctu().getNazev());
        authorities.add(authority);

        return authorities;
    }



    @Override
    public String getPassword() {
        return this.ucet.getHeslo();
    }

    @Override
    public String getUsername() {
        return this.ucet.getUcet_login();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
