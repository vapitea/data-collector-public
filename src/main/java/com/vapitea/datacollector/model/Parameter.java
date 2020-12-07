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
public class Parameter {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String unit;

  @Lob
  private String description;

  @ManyToOne()
  private DataSource dataSource;

  @Builder.Default
  @OneToMany(mappedBy = "parameter", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Measurement> measurements = new ArrayList<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Parameter parameter = (Parameter) o;
    return id.equals(parameter.id);
  }

  @Override
  public int hashCode() {
    return 31;
  }
}
