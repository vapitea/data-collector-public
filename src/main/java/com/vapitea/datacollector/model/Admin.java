package com.vapitea.datacollector.model;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

@SuperBuilder
@NoArgsConstructor
@Entity
public class Admin extends Operator {


  @Override
  public Set<GrantedAuthority> getAuthorities() {
    Set<GrantedAuthority> authorities = new HashSet<>(super.getAuthorities());
    authorities.add(new SimpleGrantedAuthority("ADMIN.doAnything"));
    return authorities;
  }
}
