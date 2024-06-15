package app.m.advise.service;

import app.m.advise.endpoint.rest.model.MedicalInfo;
import app.m.advise.endpoint.security.AuthProvider;
import app.m.advise.service.handler.TemplateHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MedicalInfoService {
  private static final String TEMPLATE = "document.html";
  private final PatientService patientService;
  private final FileService fileService;
  private final TemplateHandler templateHandler;
  private final DoctorService doctorService;
  private final DepartmentService departmentService;

  public byte[] getMedicalInfo(String pId) {
    var patient = patientService.getPatientById(pId);
    return fileService.downloadFile(patient.getDocumentId());
  }

  public MedicalInfo crupdateMedicalInfo(MedicalInfo medicalInfo) {
    var auth = AuthProvider.getUser();
    var doctor = doctorService.getDoctorById(auth.getId());
    var department = departmentService.getDepartmentById(doctor.getDepartmentId());
    byte[] fileAsByte = templateHandler.generatePdf(doctor, department, medicalInfo, TEMPLATE);
    fileService.uploadFile(medicalInfo.getPatient().getDocumentId(), fileAsByte);
    return medicalInfo;
  }
}
