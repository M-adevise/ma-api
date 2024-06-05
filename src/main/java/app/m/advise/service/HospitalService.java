package app.m.advise.service;

import app.m.advise.model.Hospital;
import app.m.advise.repository.HospitalRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HospitalService {
  private final HospitalRepository repository;

  public List<Hospital> getHospitals() {
    return repository.findAll();
  }

  public List<Hospital> crupdateHospitals(List<Hospital> hospitals) {
    return repository.saveAll(hospitals);
  }
}
