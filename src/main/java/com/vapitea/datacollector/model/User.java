package com.vapitea.datacollector.model;


import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User implements UserDetails, CredentialsContainer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String password;

  @Builder.Default
  @ManyToMany(mappedBy = "users", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<Team> teams = new ArrayList<>();


  @Builder.Default
  private Boolean accountNonExpired = true;
  @Builder.Default
  private Boolean accountNonLocked = true;
  @Builder.Default
  private Boolean credentialsNonExpired = true;
  @Builder.Default
  private Boolean enabled = true;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return id.equals(user.id);
  }

  @Override
  public int hashCode() {
    return 31;
  }

  @Override
  public Set<GrantedAuthority> getAuthorities() {
    return Set.of(

      new SimpleGrantedAuthority("USER.User.readOwn"),
      new SimpleGrantedAuthority("USER.User.read"),
      new SimpleGrantedAuthority("USER.Teams.read"),
      new SimpleGrantedAuthority("USER.Team.read"),
      new SimpleGrantedAuthority("USER.DataSources.read"),
      new SimpleGrantedAuthority("USER.DataSource.read"),
      new SimpleGrantedAuthority("USER.Parameters.read"),
      new SimpleGrantedAuthority("USER.Parameter.read"),
      new SimpleGrantedAuthority("USER.Measurements.read")
    );
  }


  @Override
  public String getUsername() {
    return name;
  }

  @Override
  public boolean isAccountNonExpired() {
    return this.accountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.accountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return this.credentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return this.enabled;
  }

  @Override
  public void eraseCredentials() {
    this.password = null;
  }

}
