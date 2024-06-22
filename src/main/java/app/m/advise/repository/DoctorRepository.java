package app.m.advise.repository;

import app.m.advise.model.Doctor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {
  @Query(
      "select d from Doctor d where d.firstName ilike %:firstname% or d.lastName ilike %:lastname%")
  List<Doctor> findByCriteria(
      @Param("firstname") String firstName, @Param("lastname") String lastName);

  Optional<Doctor> findByAuthenticationIdAndEmail(String firebaseId, String email);

  List<Doctor> findByDepartmentId(String hId);
}
