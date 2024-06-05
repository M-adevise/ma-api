package app.m.advise.repository;

import app.m.advise.model.Patient;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
  Optional<Patient> findByAuthenticationIdAndEmail(String firebaseId, String email);

  List<Patient> findByDoctorId(String dId);
}
