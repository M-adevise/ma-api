package app.m.advise.endpoint.mapper;

import app.m.advise.endpoint.rest.model.Patient;
import app.m.advise.model.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PatientRestMapper {
  private final DoctorRestMapper doctorRestMapper;

  public Patient toRest(app.m.advise.model.Patient domain) {
    return new Patient()
        .id(domain.getId())
        .firstName(domain.getFirstName())
        .lastName(domain.getLastName())
        .birthDate(domain.getBirthdate())
        .email(domain.getEmail())
        .nic(domain.getNIC())
        .doctorId(domain.getDoctorId())
        .role(toRestRole(domain.getRole()))
        .photoId(domain.getPhotoId())
        .authenticationId(domain.getAuthenticationId());
  }

  private Role toDomainRole(Patient.RoleEnum role) {
    return switch (role) {
      case ADVISOR -> Role.ADVISOR;
      case DOCTOR -> Role.DOCTOR;
      case PATIENT -> Role.PATIENT;
    };
  }

  private Patient.RoleEnum toRestRole(Role role) {
    return switch (role) {
      case ADVISOR -> Patient.RoleEnum.ADVISOR;
      case DOCTOR -> Patient.RoleEnum.DOCTOR;
      case PATIENT -> Patient.RoleEnum.PATIENT;
    };
  }

  public app.m.advise.model.Patient toDomain(Patient rest) {
    return app.m.advise.model.Patient.builder()
        .id(rest.getId())
        .firstName(rest.getFirstName())
        .lastName(rest.getLastName())
        .birthdate(rest.getBirthDate())
        .email(rest.getEmail())
        .NIC(rest.getNic())
        .doctorId(rest.getDoctorId())
        .role(toDomainRole(rest.getRole()))
        .photoId(rest.getPhotoId())
        .authenticationId(rest.getAuthenticationId())
        .build();
  }
}
