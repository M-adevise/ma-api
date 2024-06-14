package app.m.advise.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
  ADVISOR,
  DOCTOR,
  PATIENT;

  public String getRole() {
    return name();
  }

  @Override
  public String getAuthority() {
    return "ROLE_" + getRole();
  }
}
