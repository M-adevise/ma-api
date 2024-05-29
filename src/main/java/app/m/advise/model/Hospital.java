package app.m.advise.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "\"hospital\"")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Hospital {
  @Id private String id;
  private String name;
  private String NIF;
  private String STAT;
  private String contact;
  private User advisor;
}
