package app.m.advise.endpoint.mapper;

import app.m.advise.endpoint.rest.model.Patient;
import app.m.advise.endpoint.rest.model.User;
import app.m.advise.model.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PatientRestMapper {

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
        .address(domain.getAddress())
        .country(domain.getCountry())
        .city(domain.getCity())
        .sex(toUserSexEnum(domain.getSex()))
        .documentId(domain.getDocumentId())
        .authenticationId(domain.getAuthenticationId());
  }

  private User.SexEnum toPatientSexEnum(Patient.SexEnum sexEnum) {
    return switch (sexEnum) {
      case MALE -> User.SexEnum.MALE;
      case FEMININE -> User.SexEnum.FEMININE;
    };
  }

  private Patient.SexEnum toUserSexEnum(User.SexEnum sexEnum) {
    return switch (sexEnum) {
      case MALE -> Patient.SexEnum.MALE;
      case FEMININE -> Patient.SexEnum.FEMININE;
    };
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
        .address(rest.getAddress())
        .country(rest.getCountry())
        .city(rest.getCity())
        .sex(toPatientSexEnum(rest.getSex()))
        .documentId(rest.getDocumentId())
        .build();
  }
}
