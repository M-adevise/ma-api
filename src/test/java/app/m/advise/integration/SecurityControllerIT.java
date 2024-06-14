package app.m.advise.integration;

import static app.m.advise.testutils.TestUtils.BAD_TOKEN;
import static app.m.advise.testutils.TestUtils.DOCTOR1_TOKEN;
import static app.m.advise.testutils.TestUtils.anAvailablePort;
import static app.m.advise.testutils.TestUtils.setFileStorageService;
import static app.m.advise.testutils.TestUtils.setFirebaseService;
import static app.m.advise.testutils.TestUtils.toCreate;
import static app.m.advise.testutils.TestUtils.user1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import app.m.advise.AbstractContextInitializer;
import app.m.advise.endpoint.rest.api.SecurityApi;
import app.m.advise.endpoint.rest.client.ApiClient;
import app.m.advise.endpoint.rest.client.ApiException;
import app.m.advise.endpoint.rest.model.User;
import app.m.advise.service.api.firebase.FirebaseService;
import app.m.advise.service.file.FileStorageService;
import app.m.advise.testutils.TestUtils;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = SecurityControllerIT.ContextInitializer.class)
@AutoConfigureMockMvc
class SecurityControllerIT {
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
  void sign_in_ok() throws ApiException {
    ApiClient client = anApiClient(DOCTOR1_TOKEN);
    SecurityApi api = new SecurityApi(client);

    User actual = api.signIn();

    assertEquals(user1(), actual);
  }

  @Test
  void sign_up_ok() throws ApiException {
    ApiClient client = anApiClient(DOCTOR1_TOKEN);
    SecurityApi api = new SecurityApi(client);

    User actual = api.signUp(toCreate());

    assertEquals(toCreate(), actual);
  }

  @Test
  void sign_in_ko() {
    ApiClient client = anApiClient(BAD_TOKEN);
    SecurityApi api = new SecurityApi(client);

    assertThrows(ApiException.class, api::signIn);
  }

  static class ContextInitializer extends AbstractContextInitializer {
    public static final int SERVER_PORT = anAvailablePort();

    @Override
    public int getServerPort() {
      return SERVER_PORT;
    }
  }
}
