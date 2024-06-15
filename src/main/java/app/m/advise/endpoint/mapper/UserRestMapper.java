package app.m.advise.endpoint.mapper;

import app.m.advise.endpoint.rest.model.User;
import app.m.advise.model.Doctor;
import app.m.advise.model.Patient;
import app.m.advise.model.Role;
import org.springframework.stereotype.Component;

@Component
public class UserRestMapper {
  public User toRest(app.m.advise.model.User domain) {
    return new User()
        .id(domain.getId())
        .firstName(domain.getFirstName())
        .lastName(domain.getLastName())
        .nic(domain.getNIC())
        .authenticationId(domain.getAuthenticationId())
        .birthDate(domain.getBirthdate())
        .email(domain.getEmail())
        .photoId(domain.getPhotoId())
        .sex(domain.getSex())
        .address(domain.getAddress())
        .country(domain.getCountry())
        .contact(domain.getContact())
        .city(domain.getCity())
        .sex(domain.getSex())
        .role(toRestRole(domain.getRole()));
  }

  public app.m.advise.model.User toDomain(User payload) {
    return app.m.advise.model.User.builder()
        .id(payload.getId())
        .firstName(payload.getFirstName())
        .lastName(payload.getLastName())
        .NIC(payload.getNic())
        .authenticationId(payload.getAuthenticationId())
        .birthdate(payload.getBirthDate())
        .email(payload.getEmail())
        .photoId(payload.getPhotoId())
        .role(toDomainRole(payload.getRole()))
        .address(payload.getAddress())
        .country(payload.getCountry())
        .contact(payload.getContact())
        .city(payload.getCity())
        .sex(payload.getSex())
        .build();
  }

  private static Role toDomainRole(User.RoleEnum role) {
    return switch (role) {
      case ADVISOR -> Role.ADVISOR;
      case DOCTOR -> Role.DOCTOR;
      case PATIENT -> Role.PATIENT;
    };
  }

  private static User.RoleEnum toRestRole(Role role) {
    return switch (role) {
      case ADVISOR -> User.RoleEnum.ADVISOR;
      case DOCTOR -> User.RoleEnum.DOCTOR;
      case PATIENT -> User.RoleEnum.PATIENT;
    };
  }

  public static Doctor doctorFrom(app.m.advise.model.User user) {
    return Doctor.builder()
        .id(user.getId())
        .lastName(user.getLastName())
        .firstName(user.getFirstName())
        .email(user.getEmail())
        .birthdate(user.getBirthdate())
        .authenticationId(user.getAuthenticationId())
        .photoId(user.getPhotoId())
        .departmentId(null)
        .NIC(user.getNIC())
        .registryNumber(null)
        .role(user.getRole())
        .address(user.getAddress())
        .branch(null)
        .country(user.getCountry())
        .contact(user.getContact())
        .city(user.getCity())
        .sex(user.getSex())
        .build();
  }

  public static Patient patientFrom(app.m.advise.model.User user) {
    return Patient.builder()
        .id(user.getId())
        .lastName(user.getLastName())
        .firstName(user.getFirstName())
        .email(user.getEmail())
        .birthdate(user.getBirthdate())
        .authenticationId(user.getAuthenticationId())
        .photoId(user.getPhotoId())
        .NIC(user.getNIC())
        .doctorId(null)
        .address(user.getAddress())
        .country(user.getCountry())
        .city(user.getCity())
        .contact(user.getContact())
        .sex(user.getSex())
        .role(user.getRole())
        .build();
  }
}
