package app.m.advise.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserPromptResponse {
  private String id;
  private String userId;
  private String aiResponse;
  private Instant creationDatetime;
}
