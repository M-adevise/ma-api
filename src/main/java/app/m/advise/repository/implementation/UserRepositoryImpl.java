package app.m.advise.repository.implementation;

import app.m.advise.model.Doctor;
import app.m.advise.model.Patient;
import app.m.advise.model.Role;
import app.m.advise.model.User;
import app.m.advise.model.exception.BadRequestException;
import app.m.advise.model.exception.NotFoundException;
import app.m.advise.repository.UserRepository;
import app.m.advise.repository.jpa.DoctorJpaRepository;
import app.m.advise.repository.jpa.PatientJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {
  private final DoctorJpaRepository doctorJpaRepository;
  private final PatientJpaRepository patientJpaRepository;

  @Override
  public User findByAuthenticationIdAndEmail(String firebaseId, String email) {
    var doctor = doctorJpaRepository.findByAuthenticationIdAndEmail(firebaseId, email);
    if (doctor.isEmpty()) {
      return patientJpaRepository
          .findByAuthenticationIdAndEmail(firebaseId, email)
          .orElseThrow(() -> new NotFoundException(""));
    }
    return doctor.get();
  }

  @Override
  public User findById(String userId) {
    var doctor = doctorJpaRepository.findById(userId);
    if (doctor.isEmpty()) {
      return patientJpaRepository.findById(userId).orElseThrow(() -> new NotFoundException(""));
    }
    return doctor.get();
  }

  @Override
  public User save(User user) {
    if (user.getRole().equals(Role.DOCTOR)) {
      return doctorJpaRepository.save((Doctor) user);
    }
    if (user.getRole().equals(Role.PATIENT)) {
      return patientJpaRepository.save((Patient) user);
    } else {
      throw new BadRequestException("");
    }
  }
}
