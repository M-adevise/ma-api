package app.m.advise.service;

import app.m.advise.model.Doctor;
import app.m.advise.model.exception.NotFoundException;
import app.m.advise.repository.DoctorRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DoctorService {
  private final DoctorRepository repository;

  public List<Doctor> getDoctorsByDepartmentId(String hId) {
    return repository.findByDepartmentId(hId);
  }

  public Doctor getDoctorById(String id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Doctor." + id + " is not found."));
  }

  public List<Doctor> getDoctors() {
    return repository.findAll();
  }

  public Doctor crupdate(Doctor toSave) {
    return repository.save(toSave);
  }
}
