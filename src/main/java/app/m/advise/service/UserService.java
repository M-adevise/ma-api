package app.m.advise.service;

import app.m.advise.model.User;
import app.m.advise.model.exception.NotFoundException;
import app.m.advise.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository repository;

  public User findByAuthenticationIdAndEmail(String firebaseId, String email) {
    return repository
        .findByAuthenticationIdAndEmail(firebaseId, email)
        .orElseThrow(() -> new NotFoundException("User." + email + " is not found"));
  }

  public User getUserById(String userId) {
    return repository
        .findById(userId)
        .orElseThrow(() -> new NotFoundException("User." + userId + " is not found"));
  }

  public User save(User toSave) {
    return repository.save(toSave);
  }
}
