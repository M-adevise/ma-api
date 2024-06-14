package app.m.advise.endpoint.mapper;

import static app.m.advise.service.StreamTokenService.EXPIRES_IN;

import app.m.advise.TokenServerAssistant;
import app.m.advise.endpoint.rest.model.InlineResponse200;
import org.springframework.stereotype.Component;

@Component
public class StreamTokenRestMapper {

  public InlineResponse200 toRest(TokenServerAssistant.TokenInfo domain) {
    return new InlineResponse200().token(domain.data).expiresIn(EXPIRES_IN);
  }
}
