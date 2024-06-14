package app.m.advise.service;

import static app.m.advise.TokenServerAssistant.generateToken04;

import app.m.advise.TokenServerAssistant;
import app.m.advise.endpoint.rest.model.InlineObject;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class StreamTokenService {
  public static final int EXPIRES_IN = 24 * 3600;

  public TokenServerAssistant.TokenInfo generateToken(InlineObject payload) {
    return generateToken04(
        Objects.requireNonNull(payload.getAppId()).longValue(),
        payload.getUsername(),
        payload.getAppSecret(),
        EXPIRES_IN,
        null);
  }
}
