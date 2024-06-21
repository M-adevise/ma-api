package app.m.advise.integration;

import static app.m.advise.testutils.TestUtils.CHANNEL1_ID;
import static app.m.advise.testutils.TestUtils.CHANNEL2_ID;
import static app.m.advise.testutils.TestUtils.DOCTOR1_TOKEN;
import static app.m.advise.testutils.TestUtils.DOCTOR_1_ID;
import static app.m.advise.testutils.TestUtils.MESSAGE_ID;
import static app.m.advise.testutils.TestUtils.anAvailablePort;
import static app.m.advise.testutils.TestUtils.channel1;
import static app.m.advise.testutils.TestUtils.channel2;
import static app.m.advise.testutils.TestUtils.message;
import static app.m.advise.testutils.TestUtils.message2;
import static app.m.advise.testutils.TestUtils.setFileStorageService;
import static app.m.advise.testutils.TestUtils.setFirebaseService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import app.m.advise.AbstractContextInitializer;
import app.m.advise.endpoint.rest.api.CommunicationApi;
import app.m.advise.endpoint.rest.client.ApiClient;
import app.m.advise.endpoint.rest.client.ApiException;
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
@ContextConfiguration(initializers = CommunicationControllerIT.ContextInitializer.class)
@AutoConfigureMockMvc
class CommunicationControllerIT {
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
  void create_channel_ok() throws ApiException {
    ApiClient client = anApiClient(DOCTOR1_TOKEN);
    CommunicationApi api = new CommunicationApi(client);

    var actual = api.crupdateChannel(CHANNEL1_ID, channel1());

    assertEquals(channel1(), actual);
  }

  @Test
  void read_channels_ok() throws ApiException {
    ApiClient client = anApiClient(DOCTOR1_TOKEN);
    CommunicationApi api = new CommunicationApi(client);

    var actual = api.getChannels(DOCTOR_1_ID);

    assertTrue(actual.contains(channel2()));
  }

  @Test
  void send_message_ok() throws ApiException {
    ApiClient client = anApiClient(DOCTOR1_TOKEN);
    CommunicationApi api = new CommunicationApi(client);

    var actual = api.crupdateMessage(CHANNEL2_ID, MESSAGE_ID, message());

    assertEquals(message(), actual);
  }

  @Test
  void read_channel_messages_ok() throws ApiException {
    ApiClient client = anApiClient(DOCTOR1_TOKEN);
    CommunicationApi api = new CommunicationApi(client);

    var actual = api.getMessageByChannelId(CHANNEL2_ID, 1, 20);

    assertTrue(actual.contains(message2()));
  }

  static class ContextInitializer extends AbstractContextInitializer {
    public static final int SERVER_PORT = anAvailablePort();

    @Override
    public int getServerPort() {
      return SERVER_PORT;
    }
  }
}
