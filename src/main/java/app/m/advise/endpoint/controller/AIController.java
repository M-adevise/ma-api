package app.m.advise.endpoint.controller;

import app.m.advise.endpoint.mapper.AIRestMapper;
import app.m.advise.endpoint.rest.model.PromptResponse;
import app.m.advise.endpoint.rest.model.UserPrompt;
import app.m.advise.service.UserPromptService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AIController {
  private final UserPromptService service;
  private final AIRestMapper mapper;

  @PutMapping("/prompts")
  public PromptResponse sendPrompt(@RequestBody UserPrompt prompt) {
    var response = service.generateResponse(prompt.getContent());
    return mapper.toRest(response);
  }
}
