package app.m.advise.endpoint.controller;

import app.m.advise.endpoint.mapper.UserRestMapper;
import app.m.advise.endpoint.rest.model.User;
import app.m.advise.endpoint.security.AuthProvider;
import app.m.advise.endpoint.validator.UserValidator;
import app.m.advise.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SecurityController {
  private final UserService service;
  private final UserRestMapper mapper;
  private final UserValidator userValidator;

  @PostMapping("/signin")
  public User signIn() {
    var authenticatedUser = AuthProvider.getUser();
    return mapper.toRest(authenticatedUser);
  }

  @PostMapping("/signup")
  public User signUp(@RequestBody User payload) {
    userValidator.accept(payload);
    var toSave = mapper.toDomain(payload);
    var saved = service.save(toSave);
    return mapper.toRest(saved);
  }
}
