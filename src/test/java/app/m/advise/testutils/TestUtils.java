package app.m.advise.testutils;

import static app.m.advise.endpoint.rest.model.User.RoleEnum.ADVISOR;
import static app.m.advise.endpoint.rest.model.User.RoleEnum.DOCTOR;
import static app.m.advise.endpoint.rest.model.User.RoleEnum.PATIENT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import app.m.advise.endpoint.rest.client.ApiClient;
import app.m.advise.endpoint.rest.model.Department;
import app.m.advise.endpoint.rest.model.DepartmentAdvisor;
import app.m.advise.endpoint.rest.model.Doctor;
import app.m.advise.endpoint.rest.model.Hospital;
import app.m.advise.endpoint.rest.model.Patient;
import app.m.advise.endpoint.rest.model.User;
import app.m.advise.service.api.firebase.FUser;
import app.m.advise.service.api.firebase.FirebaseService;
import app.m.advise.service.api.gemini.conf.GeminiConf;
import app.m.advise.service.file.FileStorageService;
import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.Candidate;
import com.google.cloud.vertexai.api.Content;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.api.Part;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

public class TestUtils {
  public static final String DOCTOR_1_ID = "doctor1_id";
  public static final String USER1_AUTHENTICATION_ID = "user1_authentication_id";
  public static final String HOSPITAL1_ID = "hospital1_id";
  public static String VALID_TOKEN = "valid_token";
  public static String BAD_TOKEN = "bad_token";

  public static ApiClient anApiClient(String token, int serverPort) {
    ApiClient client = new ApiClient();
    client.setScheme("http");
    client.setHost("localhost");
    client.setPort(serverPort);
    client.setRequestInterceptor(
        httpRequestBuilder -> httpRequestBuilder.header("Authorization", "Bearer " + token));
    return client;
  }

  public static void setGeminiConf(GeminiConf geminiConfMock) {
    when(geminiConfMock.getModel())
        .thenReturn(new GenerativeModel("dummy", new VertexAI("dummy", "dummy")));
    when(geminiConfMock.generateContent(any()))
        .thenReturn(
            GenerateContentResponse.newBuilder()
                .addAllCandidates(
                    List.of(
                        new Candidate[] {
                          Candidate.newBuilder()
                              .setContent(
                                  Content.newBuilder()
                                      .addParts(Part.newBuilder().setText("Hi").build())
                                      .build())
                              .build()
                        }))
                .build());
  }

  public static void setFirebaseService(FirebaseService firebaseService) {
    when(firebaseService.getUserByBearer(VALID_TOKEN))
        .thenReturn(new FUser(USER1_AUTHENTICATION_ID, "user1@email.com"));
  }

  public static void setFileStorageService(FileStorageService fileStorageService)
      throws IOException {
    when(fileStorageService.downloadFile(any())).thenReturn("photo.png".getBytes());
    when(fileStorageService.uploadFile(any(), any())).thenReturn("photo.png");
  }

  public static User user1() {
    return new User()
        .id(DOCTOR_1_ID)
        .birthDate(null)
        .firstName("Ny Hasina")
        .lastName("VAGNO")
        .nic("nyhasina14")
        .email("user1@email.com")
        .photoId("photo_id")
        .role(DOCTOR)
        .authenticationId(USER1_AUTHENTICATION_ID);
  }

  public static Doctor doctor1() {
    return new Doctor()
        .id(DOCTOR_1_ID)
        .birthDate(null)
        .firstName("Ny Hasina")
        .lastName("VAGNO")
        .nic("nyhasina14")
        .email("user1@email.com")
        .photoId("photo_id")
        .registryNumber("123456")
        .department(department())
        .role(Doctor.RoleEnum.DOCTOR)
        .authenticationId(USER1_AUTHENTICATION_ID);
  }

  public static Department department() {
    return new Department()
        .id(hospital1().getId())
        .name(hospital1().getName())
        .contact(hospital1().getContact())
        .advisor(hospital1().getAdvisor());
  }

  public static User advisor() {
    return new User()
        .id("advisor1_id")
        .birthDate(null)
        .firstName("John")
        .lastName("Doe")
        .nic("advisor")
        .email("advisor@email.com")
        .photoId("photo_id")
        .role(ADVISOR)
        .authenticationId("auth_id");
  }

  public static User toCreate() {
    return new User()
        .id("user2_id")
        .birthDate(null)
        .firstName("user2")
        .lastName("user2_lastname")
        .nic("user214")
        .email("user2@email.com")
        .photoId("photo2_id")
        .role(PATIENT)
        .authenticationId("user2_auth_id");
  }

  public static Patient patient1() {
    return new Patient()
        .id("patien1_id")
        .firstName("Nicolas")
        .lastName("Jokic")
        .email("patient1@email.com")
        .birthDate(null)
        .authenticationId("patient1_authentication_id")
        .photoId("photo_id")
        .nic("151616232626")
        .role(Patient.RoleEnum.PATIENT)
        .doctor(doctor1());
  }

  public static Hospital hospital1() {
    return new Hospital()
        .id(HOSPITAL1_ID)
        .name("HJRA")
        .stat("STAT123456789")
        .nif("NIF123456789")
        .contact("+261324063616")
        .advisor(null);
  }

  public static Hospital hospital2() {
    return new Hospital()
        .id("hospital2_id")
        .name("CHU")
        .stat("STAT1234567810")
        .nif("NIF1234567810")
        .contact("+261324063617")
        .advisor(new DepartmentAdvisor().schemas(advisor()));
  }

  public static int anAvailablePort() {
    try {
      return new ServerSocket(0).getLocalPort();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
