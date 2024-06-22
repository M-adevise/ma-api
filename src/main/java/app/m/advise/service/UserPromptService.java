package app.m.advise.service;

import static java.time.Instant.now;

import app.m.advise.endpoint.security.AuthProvider;
import app.m.advise.model.UserPromptResponse;
import app.m.advise.service.api.gemini.GeminiService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserPromptService {
  private final GeminiService geminiService;

  public UserPromptResponse generateResponse(String prompt) {
    var response = geminiService.generateContent(prompt);
    var responseData =
        UserPromptResponse.builder()
            .userId(AuthProvider.getUser().getId())
            .creationDatetime(now())
            .aiResponse(response)
            .build();
    return responseData;
  }
}
