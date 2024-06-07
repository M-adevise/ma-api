package app.m.advise.repository;

import app.m.advise.model.Appointment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String> {
  List<Appointment> findByOrganizerId(String id);

  List<Appointment> findByParticipantId(String id);
}
