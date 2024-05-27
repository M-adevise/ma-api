package app.m.advise.service;

import app.m.advise.endpoint.security.AuthProvider;
import app.m.advise.model.History;
import app.m.advise.repository.HistoryRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HistoryService {
  private final HistoryRepository repository;

  public List<History> getHistory() {
    var authId = AuthProvider.getUser().getId();
    return repository.findByUserId(authId);
  }
}
