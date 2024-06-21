package app.m.advise.unit;

import static app.m.advise.endpoint.rest.model.PatientAdditionalInfo.CivilStatusEnum.MARRIED;
import static app.m.advise.endpoint.rest.model.User.SexEnum.FEMININE;
import static app.m.advise.model.Role.DOCTOR;
import static app.m.advise.testutils.TestUtils.patient1;
import static java.util.UUID.randomUUID;

import app.m.advise.endpoint.rest.model.MedicalInfo;
import app.m.advise.endpoint.rest.model.MedicalInfoContinualPostoperative;
import app.m.advise.endpoint.rest.model.MedicalInfoTreatment;
import app.m.advise.endpoint.rest.model.PatientAdditionalInfo;
import app.m.advise.model.Department;
import app.m.advise.model.Doctor;
import app.m.advise.service.handler.TemplateHandler;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TemplateHandlerTest {
  private final TemplateHandler subject = new TemplateHandler();

  @Test
  @Disabled
  //For local test only
  void generate_pdf_ok() throws IOException {
    byte[] data = subject.generatePdf(doctor(), department(), medicalInfo(), "document.html");
    File generatedFile = new File(randomUUID() + ".pdf");
    OutputStream os = new FileOutputStream(generatedFile);
    os.write(data);
    os.close();
  }

  public Doctor doctor() {
    return Doctor.builder()
        .id("doctor2_id")
        .firstName("Ny Hasina2")
        .lastName("VAGNO")
        .NIC("nyhasina15")
        .email("user2@email.com")
        .photoId("photo2_id")
        .registryNumber("123457")
        .departmentId(department().getId())
        .branch("Oncolongie")
        .country("Madagascar")
        .city("Toliara")
        .contact("+261 34 24 383 18")
        .role(DOCTOR)
        .sex(FEMININE)
        .authenticationId("user2_authentication_id")
        .build();
  }

  public Department department() {
    return Department.builder().name("CHU VAGNO").build();
  }

  public MedicalInfo medicalInfo() {
    return new MedicalInfo()
        .patientAdditionalInfo(
            new PatientAdditionalInfo()
                .civilStatus(MARRIED)
                .profession("Chauffeur")
                .bloodType("A+")
                .allergies(List.of("Penicilline", "Ampicilline")))
        .continualPostoperative(
            List.of(
                new MedicalInfoContinualPostoperative()
                    .date(Instant.now())
                    .care("Changement de pansement")
                    .followup("Amélioration notable")))
        .treatment(
            List.of(
                new MedicalInfoTreatment()
                    .disease("Fievre")
                    .stage("Modere")
                    .description("Crises fréquentes")
                    .hospital("Clinic Saint Luc")
                    .doctorName("Dr Yves")
                    .treatmentStart(Instant.now().minus(12, ChronoUnit.DAYS))
                    .treatmentEnd(Instant.now().minus(10, ChronoUnit.DAYS))))
        .patient(
            patient1()
                .address("Avenu 65, Francois de la Rue")
                .birthDate(Instant.now())
                .contact("+261 32 40 636 16"));
  }
}
