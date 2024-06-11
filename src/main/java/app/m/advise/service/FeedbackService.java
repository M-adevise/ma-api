package app.m.advise.service;

import app.m.advise.model.Feedback;
import app.m.advise.repository.FeedbackRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FeedbackService {
  private final FeedbackRepository repository;

  public List<Feedback> getDoctorsFeedbacks(String dId) {
    return repository.findByReceiverId(dId);
  }

  public Feedback crupdate(Feedback toSave) {
    return repository.save(toSave);
  }
}
