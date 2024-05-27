package app.m.advise.integration;

import static app.m.advise.testutils.TestUtils.BAD_TOKEN;
import static app.m.advise.testutils.TestUtils.VALID_TOKEN;
import static app.m.advise.testutils.TestUtils.anAvailablePort;
import static app.m.advise.testutils.TestUtils.setFileStorageService;
import static app.m.advise.testutils.TestUtils.setFirebaseService;
import static app.m.advise.testutils.TestUtils.toCreate;
import static app.m.advise.testutils.TestUtils.user1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import app.m.advise.AbstractContextInitializer;
import app.m.advise.endpoint.rest.api.UserApi;
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
@ContextConfiguration(initializers = UserControllerIT.ContextInitializer.class)
@AutoConfigureMockMvc
class UserControllerIT {
  @MockBean private FirebaseService firebaseService;
  @MockBean private FileStorageService fileStorageService;

  @BeforeEach
  void setUp() throws IOException {
    setFirebaseService(firebaseService);
    setFileStorageService(fileStorageService);
  }

  private ApiClient anApiClient(String token) {
    return TestUtils.anApiClient(token, ContextInitializer.SERVER_PORT);
  }

  @Test
  void user_read_self_by_id_ok() throws ApiException {
    ApiClient client = anApiClient(VALID_TOKEN);
    UserApi api = new UserApi(client);

    User actual = api.getUserById(user1().getId());

    assertEquals(user1(), actual);
  }

  @Test
  void other_user_read_other_by_id_ko() throws ApiException {
    ApiClient client = anApiClient(VALID_TOKEN);
    UserApi api = new UserApi(client);
    assertThrows(ApiException.class, () -> api.getUserById(toCreate().getId()));
  }

  @Test
  void read_user_by_id_ko() throws ApiException {
    ApiClient client = anApiClient(BAD_TOKEN);
    UserApi api = new UserApi(client);

    assertThrows(ApiException.class, () -> api.getUserById(toCreate().getId()));
  }

  @Test
  void read_non_existent_user_by_id_ko() throws ApiException {
    ApiClient client = anApiClient(VALID_TOKEN);
    UserApi api = new UserApi(client);

    assertThrows(ApiException.class, () -> api.getUserById("randomId"));
  }

  static class ContextInitializer extends AbstractContextInitializer {
    public static final int SERVER_PORT = anAvailablePort();

    @Override
    public int getServerPort() {
      return SERVER_PORT;
    }
  }
}
