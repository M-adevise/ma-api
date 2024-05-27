package app.m.advise.unit;

import static app.m.advise.testutils.TestUtils.setGeminiConf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import app.m.advise.service.api.gemini.GeminiService;
import app.m.advise.service.api.gemini.conf.GeminiConf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GeminiServiceTest {
  private GeminiService subject;
  private GeminiConf geminiConfMock;

  @BeforeEach
  void setUp() {
    geminiConfMock = mock();
    subject = new GeminiService(geminiConfMock);
    setGeminiConf(geminiConfMock);
  }

  @Test
  void gemini_prompt_ok() {
    var response = subject.generateContent("Hello");

    assertNotNull(response);
    assertEquals("Hi", response);
  }
}
