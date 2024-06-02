package app.m.advise.endpoint.controller;

import app.m.advise.endpoint.mapper.DoctorRestMapper;
import app.m.advise.endpoint.rest.model.Doctor;
import app.m.advise.service.DoctorService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DoctorController {
  private final DoctorService service;
  private final DoctorRestMapper mapper;

  @GetMapping("/department/{id}/doctors")
  public List<Doctor> getDoctorsByHospitalId(@PathVariable("id") String hId) {
    return service.getDoctorsByDepartmentId(hId).stream().map(mapper::toRest).toList();
  }
}
