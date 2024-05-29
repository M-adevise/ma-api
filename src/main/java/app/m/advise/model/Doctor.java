package app.m.advise.model;

import static app.m.advise.model.Role.DOCTOR;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@PrimaryKeyJoinColumn(name = "id")
@Entity
@Table(name = "\"doctor\"")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class Doctor extends User {
  @ManyToOne(cascade = ALL, fetch = LAZY)
  private Hospital hospital;

  private String registryNumber;

  public Doctor(
      String id,
      String firstName,
      String lastName,
      String email,
      Instant birthDate,
      String authenticatedId,
      String photoId,
      Hospital hospital,
      String nic,
      String registryNumber) {
    super(id, firstName, lastName, email, birthDate, authenticatedId, photoId, nic, DOCTOR);
    this.hospital = hospital;
    this.registryNumber = registryNumber;
  }
}
