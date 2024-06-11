package app.m.advise.integration;

import static app.m.advise.testutils.TestUtils.DOCTOR_1_ID;
import static app.m.advise.testutils.TestUtils.VALID_TOKEN;
import static app.m.advise.testutils.TestUtils.anAvailablePort;
import static app.m.advise.testutils.TestUtils.feedback;
import static app.m.advise.testutils.TestUtils.setFileStorageService;
import static app.m.advise.testutils.TestUtils.setFirebaseService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import app.m.advise.AbstractContextInitializer;
import app.m.advise.endpoint.rest.api.ActivityApi;
import app.m.advise.endpoint.rest.client.ApiClient;
import app.m.advise.endpoint.rest.client.ApiException;
import app.m.advise.endpoint.rest.model.Feedback;
import app.m.advise.endpoint.rest.model.FeedbackSummary;
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
@ContextConfiguration(initializers = FeedbackControllerIT.ContextInitializer.class)
@AutoConfigureMockMvc
class FeedbackControllerIT {
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
  void read_feedback_by_doctor_id_ok() throws ApiException {
    ApiClient client = anApiClient(VALID_TOKEN);
    ActivityApi api = new ActivityApi(client);

    FeedbackSummary actual = api.getDoctorFeedbacks(DOCTOR_1_ID);

    assertEquals(1, actual.getFeedbacks().size());
  }

  @Test
  void crupdate_feedbacks() throws ApiException {
    ApiClient client = anApiClient(VALID_TOKEN);
    ActivityApi api = new ActivityApi(client);

    Feedback actual = api.giveFeedBacks(DOCTOR_1_ID, feedback());

    assertEquals(feedback().creationDatetime(null), actual.creationDatetime(null));
  }

  static class ContextInitializer extends AbstractContextInitializer {
    public static final int SERVER_PORT = anAvailablePort();

    @Override
    public int getServerPort() {
      return SERVER_PORT;
    }
  }
}
