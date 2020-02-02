package com.praful.projects.session;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.praful.projects.entity.User;
import java.math.BigDecimal;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Prafulla Rathore
 */
@AllArgsConstructor
@Getter
@Setter
public class UserInfo implements UserDetails {

    private String userId;
    @JsonIgnore
    private String password;
    private String token;
    private BigDecimal walletBalance;

    public static UserInfo create(User user) {
        return new UserInfo(user.getUserId(), user.getPassword(), user.getToken(),
            user.getWalletBalance());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userId;
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
