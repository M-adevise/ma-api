package app.m.advise.endpoint.mapper;

import app.m.advise.endpoint.rest.model.Doctor;
import app.m.advise.endpoint.rest.model.User;
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
        .address(domain.getAddress())
        .branch(domain.getBranch())
        .country(domain.getCountry())
        .city(domain.getCity())
        .sex(toUserSexEnum(domain.getSex()))
        .registryNumber(domain.getRegistryNumber());
  }

  public app.m.advise.model.Doctor toDomain(Doctor rest) {
    return app.m.advise.model.Doctor.builder()
        .id(rest.getId())
        .firstName(rest.getFirstName())
        .lastName(rest.getLastName())
        .birthdate(rest.getBirthDate())
        .authenticationId(rest.getAuthenticationId())
        .photoId(rest.getPhotoId())
        .email(rest.getEmail())
        .departmentId(rest.getDepartment().getId())
        .NIC(rest.getNic())
        .role(toDomainRole(rest.getRole()))
        .address(rest.getAddress())
        .branch(rest.getBranch())
        .country(rest.getCountry())
        .city(rest.getCity())
        .sex(toDoctorSexEnum(rest.getSex()))
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

  private User.SexEnum toDoctorSexEnum(Doctor.SexEnum sexEnum) {
    return switch (sexEnum) {
      case MALE -> User.SexEnum.MALE;
      case FEMININE -> User.SexEnum.FEMININE;
    };
  }

  private Doctor.SexEnum toUserSexEnum(User.SexEnum sexEnum) {
    return switch (sexEnum) {
      case MALE -> Doctor.SexEnum.MALE;
      case FEMININE -> Doctor.SexEnum.FEMININE;
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
