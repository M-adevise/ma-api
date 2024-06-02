package app.m.advise.endpoint.mapper;

import static app.m.advise.endpoint.rest.model.User.RoleEnum.ADVISOR;

import app.m.advise.endpoint.rest.model.Hospital;
import app.m.advise.endpoint.rest.model.HospitalAdvisor;
import app.m.advise.endpoint.rest.model.User;
import app.m.advise.model.Role;
import org.springframework.stereotype.Component;

@Component
public class HospitalRestMapper {

  public Hospital toRest(app.m.advise.model.Hospital domain) {
    var advisor = domain.getAdvisor();
    var restAdvisor =
        advisor == null
            ? null
            : new HospitalAdvisor()
                .schemas(
                    new User()
                        .id(advisor.getId())
                        .firstName(advisor.getFirstName())
                        .lastName(advisor.getLastName())
                        .email(advisor.getEmail())
                        .authenticationId(advisor.getAuthenticationId())
                        .photoId(advisor.getPhotoId())
                        .birthDate(advisor.getBirthdate())
                        .nic(advisor.getNIC())
                        .role(ADVISOR));
    return new Hospital()
        .id(domain.getId())
        .name(domain.getName())
        .nif(domain.getNIF())
        .stat(domain.getSTAT())
        .contact(domain.getContact())
        .advisor(restAdvisor);
  }

  public app.m.advise.model.Hospital toDomain(Hospital rest) {
    var advisor = rest.getAdvisor().getSchemas();
    return app.m.advise.model.Hospital.builder()
        .id(rest.getId())
        .name(rest.getName())
        .NIF(rest.getNif())
        .STAT(rest.getStat())
        .contact(rest.getContact())
        .advisor(
            app.m.advise.model.User.builder()
                .id(advisor.getId())
                .firstName(advisor.getFirstName())
                .lastName(advisor.getLastName())
                .email(advisor.getEmail())
                .authenticationId(advisor.getAuthenticationId())
                .photoId(advisor.getPhotoId())
                .birthdate(advisor.getBirthDate())
                .NIC(advisor.getNic())
                .role(Role.ADVISOR)
                .build())
        .build();
  }
}
