package app.m.advise.service;

import app.m.advise.model.Patient;
import app.m.advise.model.exception.NotFoundException;
import app.m.advise.repository.PatientRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PatientService {
  private final PatientRepository repository;

  public List<Patient> getPatientsByDoctorId(String dId) {
    return repository.findByDoctorId(dId);
  }

  public Patient getPatientById(String id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Patient." + id + " is not found."));
  }
}
