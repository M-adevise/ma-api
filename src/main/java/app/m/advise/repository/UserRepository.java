package app.m.advise.repository;

import app.m.advise.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface UserRepository {
  User findByAuthenticationIdAndEmail(String firebaseId, String email);

  User findById(String userId);

  User save(User user);
}
