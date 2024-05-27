package app.m.advise.repository;

import app.m.advise.model.History;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, String> {
  List<History> findByUserId(String userId);
}
