package app.m.advise.endpoint.mapper;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

import app.m.advise.endpoint.rest.model.Appointment;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AppointmentRestMapper {
  private final DoctorRestMapper doctorRestMapper;
  private final PatientRestMapper patientRestMapper;

  public Appointment toRest(app.m.advise.model.Appointment domain) {
    var roomId = domain.getRoomId() == null ? null : UUID.fromString(domain.getRoomId());
    return new Appointment()
        .id(domain.getId())
        .from(domain.getFrom())
        .to(domain.getTo())
        .summary(domain.getSummary())
        .roomId(roomId)
        .organizer(doctorRestMapper.toRest(domain.getOrganizer()))
        .participant(patientRestMapper.toRest(domain.getParticipant()));
  }

  public app.m.advise.model.Appointment toDomain(Appointment rest) {
    return app.m.advise.model.Appointment.builder()
        .id(rest.getId())
        .summary(rest.getSummary())
        .from(rest.getFrom())
        .to(rest.getTo())
        .creationDatetime(now())
        .roomId(randomUUID().toString())
        .organizer(doctorRestMapper.toDomain(rest.getOrganizer()))
        .participant(patientRestMapper.toDomain(rest.getParticipant()))
        .build();
  }
}
