package app.m.advise.endpoint.controller;

import app.m.advise.endpoint.mapper.HospitalRestMapper;
import app.m.advise.endpoint.rest.model.Hospital;
import app.m.advise.service.HospitalService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class HospitalController {
  private final HospitalService service;
  private final HospitalRestMapper mapper;

  @GetMapping("/hospitals")
  public List<Hospital> getHospitals() {
    return service.getHospitals().stream().map(mapper::toRest).toList();
  }
}
