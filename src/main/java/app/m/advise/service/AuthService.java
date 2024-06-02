package app.m.advise.service;

import app.m.advise.model.Doctor;
import app.m.advise.model.Patient;
import app.m.advise.model.Role;
import app.m.advise.model.User;
import app.m.advise.model.exception.NotFoundException;
import app.m.advise.repository.DoctorRepository;
import app.m.advise.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
  private final DoctorRepository doctorRepository;
  private final PatientRepository patientRepository;

  public User findByAuthenticationIdAndEmail(String id, String email) {
    var patient = patientRepository.findByAuthenticationIdAndEmail(id, email);
    if (patient.isEmpty()) {
      return doctorRepository
          .findByAuthenticationIdAndEmail(id, email)
          .orElseThrow(() -> new NotFoundException("User." + email + " is not found."));
    }
    return patient.get();
  }

  public void updateUserPic(User user, String photoId) {
    var doctor = doctorRepository.findById(user.getId());
    if (doctor.isEmpty()) {
      var patient =
          patientRepository
              .findById(user.getId())
              .orElseThrow(() -> new NotFoundException("User." + user.getId() + " is not found."));
      patient.setPhotoId(photoId);
      patientRepository.save(patient);
    }
    doctor.get().setPhotoId(photoId);
    doctorRepository.save(doctor.get());
  }

  public User save(User user) {
    if (user.getRole().equals(Role.DOCTOR)) {
      return doctorRepository.save((Doctor) user);
    }
    return patientRepository.save((Patient) user);
  }
}
