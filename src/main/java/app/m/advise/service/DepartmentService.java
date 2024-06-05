package app.m.advise.service;

import app.m.advise.model.Department;
import app.m.advise.model.exception.NotFoundException;
import app.m.advise.repository.HospitalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepartmentService {
  private final HospitalRepository hospitalRepository;

  public Department getDepartmentById(String id) {
    return hospitalRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Department." + id + " is not found."));
  }
}
