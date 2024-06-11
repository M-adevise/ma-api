package app.m.advise.repository;

import app.m.advise.model.Feedback;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, String> {
  List<Feedback> findByReceiverId(String id);
}
