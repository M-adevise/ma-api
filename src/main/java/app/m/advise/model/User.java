package app.m.advise.model;

import static jakarta.persistence.EnumType.STRING;
import static org.hibernate.type.SqlTypes.NAMED_ENUM;

import jakarta.persistence.*;
import java.time.Instant;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;

@MappedSuperclass
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public sealed class User permits Doctor, Patient {
  @Id private String id;

  private String firstName;
  private String lastName;

  @Column(unique = true)
  private String email;

  @Timestamp private Instant birthdate;

  @Column(unique = true)
  private String authenticationId;

  @Column(unique = true)
  private String photoId;

  @Column(unique = true)
  private String NIC;

  @Enumerated(STRING)
  @JdbcTypeCode(NAMED_ENUM)
  private Role role;

  public enum Role {
    ADMIN,
    DOCTOR,
    PATIENT
  }
}
