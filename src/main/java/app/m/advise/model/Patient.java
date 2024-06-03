package app.m.advise.model;

import static app.m.advise.model.Role.PATIENT;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@PrimaryKeyJoinColumn(name = "id")
@Entity
@Table(name = "\"patient\"")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class Patient extends User {
  @ManyToOne(cascade = ALL, fetch = LAZY)
  @JoinColumn(name = "doctor_id")
  private Doctor doctor;

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
