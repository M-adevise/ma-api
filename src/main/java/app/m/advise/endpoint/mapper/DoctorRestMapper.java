package app.m.advise.endpoint.mapper;

import app.m.advise.endpoint.rest.model.Doctor;
import app.m.advise.model.Role;
import app.m.advise.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DoctorRestMapper {
  private final DepartmentService departmentService;
  private final DepartmentRestMapper departmentRestMapper;

  public Doctor toRest(app.m.advise.model.Doctor domain) {
    var department = departmentService.getDepartmentById(domain.getDepartmentId());
    return new Doctor()
        .id(domain.getId())
        .firstName(domain.getFirstName())
        .lastName(domain.getLastName())
        .birthDate(domain.getBirthdate())
        .email(domain.getEmail())
        .department(departmentRestMapper.toRest(department))
        .nic(domain.getNIC())
        .role(toRestRole(domain.getRole()))
        .photoId(domain.getPhotoId())
        .authenticationId(domain.getAuthenticationId())
        .registryNumber(domain.getRegistryNumber());
  }

  public app.m.advise.model.Doctor toDomain(Doctor rest) {
    return app.m.advise.model.Doctor.builder()
        .id(rest.getId())
        .firstName(rest.getFirstName())
        .lastName(rest.getLastName())
        .birthdate(rest.getBirthDate())
        .email(rest.getEmail())
        .departmentId(rest.getDepartment().getId())
        .NIC(rest.getNic())
        .role(toDomainRole(rest.getRole()))
        .registryNumber(rest.getRegistryNumber())
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
