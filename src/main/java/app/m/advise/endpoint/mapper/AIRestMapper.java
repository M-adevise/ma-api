package app.m.advise.endpoint.mapper;

import app.m.advise.endpoint.rest.model.PromptResponse;
import app.m.advise.model.UserPromptResponse;
import org.springframework.stereotype.Component;

@Component
public class AIRestMapper {
  public PromptResponse toRest(UserPromptResponse domain) {
    return new PromptResponse()
        .id(domain.getId())
        .content(domain.getAiResponse())
        .creationDatetime(domain.getCreationDatetime());
  }
}
