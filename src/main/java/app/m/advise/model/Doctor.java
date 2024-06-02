package app.m.advise.model;

import static app.m.advise.model.Role.DOCTOR;

import jakarta.persistence.Entity;
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
@Table(name = "\"doctor\"")
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class Doctor extends User {
  private String departmentId;

  private String registryNumber;

  public Doctor(
      String id,
      String firstName,
      String lastName,
      String email,
      Instant birthDate,
      String authenticatedId,
      String photoId,
      String department,
      String nic,
      String registryNumber) {
    super(id, firstName, lastName, email, birthDate, authenticatedId, photoId, nic, DOCTOR);
    this.departmentId = department;
    this.registryNumber = registryNumber;
  }
}
