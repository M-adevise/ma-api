package app.m.advise.endpoint.mapper;

import app.m.advise.endpoint.rest.model.Doctor;
import app.m.advise.model.Role;
import org.springframework.stereotype.Component;

@Component
public class DoctorRestMapper {
  public Doctor toRest(app.m.advise.model.Doctor domain) {
    return new Doctor()
        .id(domain.getId())
        .firstName(domain.getFirstName())
        .lastName(domain.getLastName())
        .birthDate(domain.getBirthdate())
        .email(domain.getEmail())
        .departmentId(domain.getDepartmentId())
        .nic(domain.getNIC())
        .role(toRestRole(domain.getRole()))
        .photoId(domain.getPhotoId())
        .authenticationId(domain.getAuthenticationId())
        .registryNumber(domain.getRegistryNumber());
  }

  public app.m.advise.model.Doctor toDomain(Doctor domain) {
    return app.m.advise.model.Doctor.builder()
        .id(domain.getId())
        .firstName(domain.getFirstName())
        .lastName(domain.getLastName())
        .birthdate(domain.getBirthDate())
        .email(domain.getEmail())
        .departmentId(domain.getDepartmentId())
        .NIC(domain.getNic())
        .role(toDomainRole(domain.getRole()))
        .registryNumber(domain.getRegistryNumber())
        .build();
  }

  private Role toDomainRole(Doctor.RoleEnum role) {
    return switch (role) {
      case ADVISOR -> Role.ADVISOR;
      case DOCTOR -> Role.DOCTOR;
      case PATIENT -> Role.PATIENT;
    };
  }

  private Doctor.RoleEnum toRestRole(Role role) {
    return switch (role) {
      case ADVISOR -> Doctor.RoleEnum.ADVISOR;
      case DOCTOR -> Doctor.RoleEnum.DOCTOR;
      case PATIENT -> Doctor.RoleEnum.PATIENT;
    };
  }
}
