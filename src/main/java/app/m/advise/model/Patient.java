package app.m.advise.model;

import static app.m.advise.model.User.Role.PATIENT;

import java.time.Instant;

public final class Patient extends User {
  public Patient(
      String id,
      String firstName,
      String lastName,
      String email,
      Instant birthDate,
      String authenticatedId,
      String photoId,
      String username) {
    super(id, firstName, lastName, email, birthDate, authenticatedId, photoId, username, PATIENT);
  }
}
