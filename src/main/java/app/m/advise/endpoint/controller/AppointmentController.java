package app.m.advise.endpoint.controller;

import app.m.advise.endpoint.mapper.AppointmentRestMapper;
import app.m.advise.endpoint.rest.model.Appointment;
import app.m.advise.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AppointmentController {
  private final AppointmentService service;
  private final AppointmentRestMapper mapper;

  @PutMapping("/appointments/{id}")
  public Appointment crupdateAppointment(@RequestBody Appointment appointment) {
    var toSave = mapper.toDomain(appointment);
    return mapper.toRest(service.crupdateAppointment(toSave));
  }

  @GetMapping("/appointments/{id}")
  public Appointment getAppointmentById(@PathVariable("id") String id) {
    return mapper.toRest(service.getAppointmentById(id));
  }
}
