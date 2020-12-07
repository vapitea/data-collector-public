package com.vapitea.datacollector.model;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

@SuperBuilder
@Entity
@NoArgsConstructor
public class Operator extends User {

  @Override
  public Set<GrantedAuthority> getAuthorities() {

    Set<GrantedAuthority> authorities = new HashSet<>(super.getAuthorities());
    authorities.addAll(Set.of(
      new SimpleGrantedAuthority("OPERATOR.Team.Users.read"),
      new SimpleGrantedAuthority("OPERATOR.DataSource.create"),
      new SimpleGrantedAuthority("OPERATOR.DataSource.update"),
      new SimpleGrantedAuthority("OPERATOR.DataSource.delete"),
      new SimpleGrantedAuthority("OPERATOR.Parameter.create"),
      new SimpleGrantedAuthority("OPERATOR.Parameter.update"),
      new SimpleGrantedAuthority("OPERATOR.Parameter.delete")
    ));
    return authorities;
  }
}
