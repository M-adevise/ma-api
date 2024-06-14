package app.m.advise.endpoint.controller;

import app.m.advise.endpoint.mapper.StreamTokenRestMapper;
import app.m.advise.endpoint.rest.model.InlineObject;
import app.m.advise.endpoint.rest.model.InlineResponse200;
import app.m.advise.service.StreamTokenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class StreamTokenController {
  private final StreamTokenService service;
  private final StreamTokenRestMapper mapper;

  @PostMapping("/call/tokens")
  public InlineResponse200 generateToken(@RequestBody InlineObject body) {
    return mapper.toRest(service.generateToken(body));
  }
}
