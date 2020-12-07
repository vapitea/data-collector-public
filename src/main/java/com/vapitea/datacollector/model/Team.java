package com.vapitea.datacollector.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @Lob
  private String description;

  @Builder.Default
  @ManyToMany
  @JoinTable(name = "team_user",
    joinColumns = @JoinColumn(name = "team_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private List<User> users = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "team", cascade = {CascadeType.ALL}, orphanRemoval = true)
  private List<DataSource> dataSources = new ArrayList<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Team team = (Team) o;
    return id.equals(team.id);
  }

  @Override
  public int hashCode() {
    return 31;
  }

}
