package com.daca.listapramim.api.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public class Payload implements UserDetails {
    private static final long serialVersionUID = -4406190873913296752L;

    private String u;
    private Set<Privilege> p;
    private boolean e;
    private boolean l;
    private boolean n;
    private Long id;
    public Payload(
            String username,
            Set<Privilege> privileges,
            boolean enabled,
            boolean locked,
            boolean needChangePassword) {
        this.u = username;
        this.p = privileges;
        this.e = enabled;
        this.l = locked;
        this.n = needChangePassword;
    }

    public Payload(String u, Set<Privilege> p, Long id) {
        this.u = u;
        this.p = p;
        this.id = id;
    }






    public void setUsername(String username) {
        this.u = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        int size = this.p.size();
        // @formatter:off
        String[] privileges = Arrays
                .stream(this.p.toArray(new Privilege[size]))
                .map(Enum::name)
                .toArray(String[]::new);
        // @formatter:on
        return AuthorityUtils.createAuthorityList(privileges);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.u;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !l;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return e;
    }

    public boolean isNeedChangePassword() {
        return n;
    }

    public void setNeedChangePassword(boolean needChangePassword) {
        this.n = needChangePassword;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("username: " + u);
        sb.append(" roles: ");
        sb.append("enable: " + e);
        sb.append(" locked: " + l);
        return sb.toString();
    }
}
