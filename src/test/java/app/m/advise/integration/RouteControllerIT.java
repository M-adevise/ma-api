package app.m.advise.integration;

import static app.m.advise.testutils.TestUtils.VALID_TOKEN;
import static app.m.advise.testutils.TestUtils.anAvailablePort;
import static app.m.advise.testutils.TestUtils.setFileStorageService;
import static app.m.advise.testutils.TestUtils.setFirebaseService;
import static app.m.advise.testutils.TestUtils.setGeminiConf;
import static app.m.advise.testutils.TestUtils.setTravelApi;
import static app.m.advise.testutils.TestUtils.travelDescription;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import app.m.advise.AbstractContextInitializer;
import app.m.advise.endpoint.rest.api.RouteApi;
import app.m.advise.endpoint.rest.client.ApiClient;
import app.m.advise.endpoint.rest.client.ApiException;
import app.m.advise.endpoint.rest.model.Itinerary;
import app.m.advise.service.api.firebase.FirebaseService;
import app.m.advise.service.api.gemini.conf.GeminiConf;
import app.m.advise.service.api.travelco.TravelCO2Api;
import app.m.advise.service.file.FileStorageService;
import app.m.advise.testutils.TestUtils;
import java.io.IOException;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = RouteControllerIT.ContextInitializer.class)
@AutoConfigureMockMvc
class RouteControllerIT {
  @MockBean private GeminiConf geminiConf;
  @MockBean private TravelCO2Api travelCO2Api;
  @MockBean private FirebaseService firebaseService;
  @MockBean private FileStorageService fileStorageService;

  @BeforeEach
  void setUp() throws IOException {
    setGeminiConf(geminiConf);
    setFirebaseService(firebaseService);
    setTravelApi(travelCO2Api);
    setFileStorageService(fileStorageService);
  }

  private ApiClient anApiClient(String token) {
    return TestUtils.anApiClient(token, ContextInitializer.SERVER_PORT);
  }

  @Test
  void generate_itineraries_ok() throws ApiException {
    ApiClient client = anApiClient(VALID_TOKEN);
    RouteApi api = new RouteApi(client);

    Itinerary actual = api.generateItineraries(travelDescription());

    assertEquals("Hi", actual.getTravelDescription());
    assertEquals(new BigDecimal("10.3"), actual.getTransport().getCo2e());
    assertEquals(new BigDecimal("2.36"), actual.getTransport().getCo2ePp());
  }

  static class ContextInitializer extends AbstractContextInitializer {
    public static final int SERVER_PORT = anAvailablePort();

    @Override
    public int getServerPort() {
      return SERVER_PORT;
    }
  }
}
