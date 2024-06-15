package app.m.advise.model;

import static jakarta.persistence.EnumType.STRING;
import static org.hibernate.type.SqlTypes.NAMED_ENUM;

import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
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
public class User implements Serializable {
  @Id private String id;

  private String firstName;
  private String lastName;
  private String email;

  @Timestamp private Instant birthdate;

  private String authenticationId;

  private String photoId;

  private String NIC;

  @Enumerated(STRING)
  @JdbcTypeCode(NAMED_ENUM)
  private app.m.advise.endpoint.rest.model.User.SexEnum sex;

  private String contact;
  private String address;
  private String country;
  private String city;

  @Enumerated(STRING)
  @JdbcTypeCode(NAMED_ENUM)
  private Role role;
}
