package app.m.advise.service;

import app.m.advise.model.Appointment;
import app.m.advise.model.exception.NotFoundException;
import app.m.advise.repository.AppointmentRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppointmentService {
  private final AppointmentRepository repository;

  public Appointment crupdateAppointment(Appointment appointment) {
    return repository.save(appointment);
  }

  public Appointment getAppointmentById(String id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Appointment." + id + " is not found."));
  }

  public List<Appointment> getAppointmentByDoctorId(String id) {
    return repository.findByOrganizerId(id);
  }

  public List<Appointment> getAppointmentByPatientId(String id) {
    return repository.findByParticipantId(id);
  }
}
