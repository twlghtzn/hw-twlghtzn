package com.greenfoxacademy.homeworktwlghtzn.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenfoxacademy.homeworktwlghtzn.users.User;
import java.util.Collection;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {

  private final long id;

  private final String username;

  @JsonIgnore
  private final String password;

  private Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(long id, String username, String password) {
    this.id = id;
    this.username = username;
    this.password = password;
  }

  public static UserDetailsImpl build(User user) {
    return new UserDetailsImpl(
        user.getUserId(),
        user.getUsername(),
        user.getPassword());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  public long getId() {
    return id;
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

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    UserDetailsImpl user = (UserDetailsImpl) object;
    return Objects.equals(id, user.id);
  }
}
