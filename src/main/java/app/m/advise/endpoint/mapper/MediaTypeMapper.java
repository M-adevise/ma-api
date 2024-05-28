package app.m.advise.endpoint.mapper;

import org.apache.tika.Tika;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class MediaTypeMapper {

  public static MediaType parseMediaTypeFromBytes(byte[] bytes) {
    Tika tika = new Tika();
    String guessedMediaTypeValue = tika.detect(bytes);
    return MediaType.parseMediaType(guessedMediaTypeValue);
  }
}
