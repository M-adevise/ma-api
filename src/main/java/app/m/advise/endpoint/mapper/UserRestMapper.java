package app.m.advise.endpoint.mapper;

import app.m.advise.endpoint.rest.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserRestMapper {

  public User toRest(app.m.advise.model.User domain) {
    return new User()
        .id(domain.getId())
        .firstName(domain.getFirstName())
        .lastName(domain.getLastName())
        .birthDate(domain.getBirthdate())
        .username(domain.getNIC())
        .authenticationId(domain.getAuthenticationId())
        .email(domain.getEmail())
        .photoId(domain.getPhotoId());
  }

  public app.m.advise.model.User toDomain(User rest) {
    return app.m.advise.model.User.builder()
        .id(rest.getId())
        .firstName(rest.getFirstName())
        .lastName(rest.getLastName())
        .NIC(rest.getUsername())
        .birthdate(rest.getBirthDate())
        .authenticationId(rest.getAuthenticationId())
        .email(rest.getEmail())
        .photoId(rest.getPhotoId())
        .build();
  }
}
