package app.m.advise.model;

import static app.m.advise.model.Role.PATIENT;

import jakarta.persistence.Entity;
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
@Table(name = "\"patient\"")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class Patient extends User {
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
