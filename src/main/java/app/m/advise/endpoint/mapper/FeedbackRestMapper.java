package app.m.advise.endpoint.mapper;

import static java.util.UUID.randomUUID;

import app.m.advise.endpoint.rest.model.Feedback;
import app.m.advise.endpoint.rest.model.FeedbackSummary;
import app.m.advise.model.Doctor;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FeedbackRestMapper {
  private final PatientRestMapper patientRestMapper;

  public Feedback toRest(app.m.advise.model.Feedback domain) {
    return new Feedback()
        .comment(domain.getComment())
        .score(domain.getScore())
        .creationDatetime(domain.getCreationDatetime())
        .sender(patientRestMapper.toRest(domain.getSender()));
  }

  public FeedbackSummary toSummary(List<Feedback> feedbacks) {
    int score =
        feedbacks.stream()
                .map(Feedback::getScore)
                .reduce(0, (a, b) -> a != null ? Integer.sum(a, b) : 0)
            / feedbacks.size();
    return new FeedbackSummary()
        .totalScore(score)
        .totalFeedbacks(feedbacks.size())
        .feedbacks(feedbacks);
  }

  public app.m.advise.model.Feedback toDomain(Feedback rest, Doctor doctor) {
    return app.m.advise.model.Feedback.builder()
        .id(randomUUID().toString())
        .comment(rest.getComment())
        .score(rest.getScore())
        .creationDatetime(rest.getCreationDatetime())
        .sender(patientRestMapper.toDomain(rest.getSender()))
        .receiver(doctor)
        .build();
  }
}
