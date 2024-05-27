package app.m.advise.endpoint.controller;

import app.m.advise.endpoint.mapper.UserRestMapper;
import app.m.advise.endpoint.rest.model.User;
import app.m.advise.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
  private final UserService service;
  private final UserRestMapper mapper;

  @GetMapping("/users/{id}")
  public User getUserById(@PathVariable("id") String uId) {
    var user = service.getUserById(uId);
    return mapper.toRest(user);
  }
}
