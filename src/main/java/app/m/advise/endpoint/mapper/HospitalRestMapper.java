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
    return new Hospital()
        .id(domain.getId())
        .name(domain.getName())
        .nif(domain.getNIF())
        .stat(domain.getSTAT())
        .advisor(
            new HospitalAdvisor()
                .schemas(
                    new User()
                        .id(domain.getAdvisor().getId())
                        .firstName(domain.getAdvisor().getFirstName())
                        .lastName(domain.getAdvisor().getLastName())
                        .email(domain.getAdvisor().getEmail())
                        .authenticationId(domain.getAdvisor().getAuthenticationId())
                        .photoId(domain.getAdvisor().getPhotoId())
                        .birthDate(domain.getAdvisor().getBirthdate())
                        .nic(domain.getAdvisor().getNIC())
                        .role(ADVISOR)));
  }

  public app.m.advise.model.Hospital toDomain(Hospital rest) {
    var advisor = rest.getAdvisor().getSchemas();
    return app.m.advise.model.Hospital.builder()
        .id(rest.getId())
        .name(rest.getName())
        .NIF(rest.getNif())
        .STAT(rest.getStat())
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
