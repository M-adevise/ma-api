package app.m.advise.model;

import static app.m.advise.model.User.Role.DOCTOR;

import java.time.Instant;

public final class Doctor extends User {
  public Doctor(
      String id,
      String firstName,
      String lastName,
      String email,
      Instant birthDate,
      String authenticatedId,
      String photoId,
      String username) {
    super(id, firstName, lastName, email, birthDate, authenticatedId, photoId, username, DOCTOR);
  }
}
