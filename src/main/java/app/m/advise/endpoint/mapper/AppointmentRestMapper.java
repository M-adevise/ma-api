package app.m.advise.endpoint.mapper;

import static java.time.Instant.now;

import app.m.advise.endpoint.rest.model.Appointment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AppointmentRestMapper {
  private final DoctorRestMapper doctorRestMapper;
  private final PatientRestMapper patientRestMapper;

  public Appointment toRest(app.m.advise.model.Appointment domain) {
    return new Appointment()
        .id(domain.getId())
        .from(domain.getFrom())
        .to(domain.getTo())
        .summary(domain.getSummary())
        .organizer(doctorRestMapper.toRest(domain.getOrganizer()))
        .participants(patientRestMapper.toRest(domain.getParticipant()));
  }

  public app.m.advise.model.Appointment toDomain(Appointment rest) {
    return app.m.advise.model.Appointment.builder()
        .id(rest.getId())
        .summary(rest.getSummary())
        .from(rest.getFrom())
        .to(rest.getTo())
        .creationDatetime(now())
        .organizer(doctorRestMapper.toDomain(rest.getOrganizer()))
        .participant(patientRestMapper.toDomain(rest.getParticipants()))
        .build();
  }
}
