package app.m.advise.repository;

import app.m.advise.model.Doctor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {
  Optional<Doctor> findByAuthenticationIdAndEmail(String firebaseId, String email);
}
