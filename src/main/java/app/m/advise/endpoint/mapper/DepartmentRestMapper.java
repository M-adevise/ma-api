package app.m.advise.endpoint.mapper;

import static app.m.advise.endpoint.rest.model.User.RoleEnum.ADVISOR;

import app.m.advise.endpoint.rest.model.Department;
import app.m.advise.endpoint.rest.model.DepartmentAdvisor;
import app.m.advise.endpoint.rest.model.User;
import org.springframework.stereotype.Component;

@Component
public class DepartmentRestMapper {

  public Department toRest(app.m.advise.model.Department domain) {
    var advisor = domain.getAdvisor();
    var restAdvisor =
        advisor == null
            ? null
            : new DepartmentAdvisor()
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
    return new Department()
        .id(domain.getId())
        .name(domain.getName())
        .contact(domain.getContact())
        .advisor(restAdvisor);
  }
}
