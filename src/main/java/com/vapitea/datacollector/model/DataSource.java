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
public class DataSource {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String uuid;
  private String name;
  private String location;
  @Lob
  private String description;


  @Builder.Default
  @OneToMany(mappedBy = "dataSource", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Parameter> parameters = new ArrayList<>();

  @ManyToOne
  private Team team;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DataSource that = (DataSource) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return 31;
  }
}
