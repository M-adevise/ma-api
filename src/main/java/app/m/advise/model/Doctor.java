package app.m.advise.model;

import static app.m.advise.model.User.Role.DOCTOR;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Entity
@Table(name = "\"doctor\"")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public final class Doctor extends User {
  @ManyToOne(cascade = ALL, fetch = LAZY)
  private final Hospital hospital;

  private final String registryNumber;

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
