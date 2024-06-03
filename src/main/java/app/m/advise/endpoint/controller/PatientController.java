package app.m.advise.endpoint.controller;

import app.m.advise.endpoint.mapper.PatientRestMapper;
import app.m.advise.endpoint.rest.model.Patient;
import app.m.advise.service.PatientService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PatientController {
  private final PatientService service;
  private final PatientRestMapper mapper;

  @GetMapping("/doctors/{id}/patients")
  public List<Patient> getPatientsByDoctorId(@PathVariable("id") String dId) {
    return service.getPatientsByDoctorId(dId).stream().map(mapper::toRest).toList();
  }

  @GetMapping("/patients/{id}")
  public Patient getPatientById(@PathVariable("id") String id) {
    return mapper.toRest(service.getPatientById(id));
  }
}
