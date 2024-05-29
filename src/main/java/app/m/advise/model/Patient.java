package app.m.advise.model;

import static app.m.advise.model.User.Role.PATIENT;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "\"patient\"")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public final class Patient extends User {
  public Patient(
      String id,
      String firstName,
      String lastName,
      String email,
      Instant birthDate,
      String authenticatedId,
      String photoId,
      String NIC) {
    super(id, firstName, lastName, email, birthDate, authenticatedId, photoId, NIC, PATIENT);
  }
}
