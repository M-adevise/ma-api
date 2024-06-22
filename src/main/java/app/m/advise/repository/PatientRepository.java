package app.m.advise.repository;

import app.m.advise.model.Patient;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
  @Query(
      "select p from Patient p where p.lastName ilike %:firstname% or p.lastName ilike %:lastname%")
  List<Patient> getCriteria(
      @Param("firstname") String firstName, @Param("lastname") String lastName);

  Optional<Patient> findByAuthenticationIdAndEmail(String firebaseId, String email);

  List<Patient> findByDoctorId(String dId);
}
