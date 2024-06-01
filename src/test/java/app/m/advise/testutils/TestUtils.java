package app.m.advise.testutils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import app.m.advise.endpoint.rest.client.ApiClient;
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
  private static final String USER1_ID = "user1_id";
  private static final String USER1_AUTHENTICATION_ID = "user1_authentication_id";
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
        .id(USER1_ID)
        .birthDate(null)
        .firstName("nyhasina")
        .lastName("vagno")
        .nic("nyhasina14")
        .email("user1@email.com")
        .photoId("photo_id")
        .authenticationId(USER1_AUTHENTICATION_ID);
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
        .authenticationId("user2_auth_id");
  }

  public static int anAvailablePort() {
    try {
      return new ServerSocket(0).getLocalPort();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
