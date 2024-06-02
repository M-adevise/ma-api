package app.m.advise.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@PrimaryKeyJoinColumn(name = "id")
@Entity
@Table(name = "\"hospital\"")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class Hospital extends Department {
  private String NIF;
  private String STAT;

  public Hospital(String id, String name, String contact, User advisor, String nif, String stat) {
    super(id, name, contact, advisor);
    NIF = nif;
    STAT = stat;
  }
}
