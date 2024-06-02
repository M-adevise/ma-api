package app.m.advise.integration;

import static app.m.advise.testutils.TestUtils.VALID_TOKEN;
import static app.m.advise.testutils.TestUtils.anAvailablePort;
import static app.m.advise.testutils.TestUtils.hospital1;
import static app.m.advise.testutils.TestUtils.hospital2;
import static app.m.advise.testutils.TestUtils.setFileStorageService;
import static app.m.advise.testutils.TestUtils.setFirebaseService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import app.m.advise.AbstractContextInitializer;
import app.m.advise.endpoint.rest.api.DepartmentApi;
import app.m.advise.endpoint.rest.client.ApiClient;
import app.m.advise.endpoint.rest.client.ApiException;
import app.m.advise.endpoint.rest.model.Hospital;
import app.m.advise.service.api.firebase.FirebaseService;
import app.m.advise.service.file.FileStorageService;
import app.m.advise.testutils.TestUtils;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = HospitalControllerIT.ContextInitializer.class)
@AutoConfigureMockMvc
class HospitalControllerIT {
  @MockBean private FirebaseService firebaseServiceMock;
  @MockBean private FileStorageService fileStorageService;

  @BeforeEach
  void setUp() throws IOException {
    setFirebaseService(firebaseServiceMock);
    setFileStorageService(fileStorageService);
  }

  private ApiClient anApiClient(String token) {
    return TestUtils.anApiClient(token, ContextInitializer.SERVER_PORT);
  }

  @Test
  @Order(1)
  void read_hospitals_ok() throws ApiException {
    ApiClient client = anApiClient(VALID_TOKEN);
    DepartmentApi api = new DepartmentApi(client);

    List<Hospital> actual = api.getHospitals();

    assertTrue(actual.contains(hospital1()));
  }

  @Test
  @Order(2)
  void crupdate_hospitals_ok() throws ApiException {
    ApiClient client = anApiClient(VALID_TOKEN);
    DepartmentApi api = new DepartmentApi(client);

    List<Hospital> actual = api.crupdateHospital(List.of(hospital2()));

    assertEquals(1, actual.size());
    assertEquals(hospital2(), actual.get(0));
  }

  static class ContextInitializer extends AbstractContextInitializer {
    public static final int SERVER_PORT = anAvailablePort();

    @Override
    public int getServerPort() {
      return SERVER_PORT;
    }
  }
}
