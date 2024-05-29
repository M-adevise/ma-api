package app.m.advise.repository.jpa;

import app.m.advise.model.Doctor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorJpaRepository extends JpaRepository<Doctor, String> {
  Optional<Doctor> findByAuthenticationIdAndEmail(String firebaseId, String email);
}
