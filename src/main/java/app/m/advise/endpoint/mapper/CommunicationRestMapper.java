package app.m.advise.endpoint.mapper;

import static org.apache.commons.io.file.FilesUncheck.createTempDirectory;

import app.m.advise.endpoint.rest.model.Channel;
import app.m.advise.endpoint.rest.model.Message;
import java.io.File;
import java.nio.file.Files;
import java.util.Base64;
import javax.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommunicationRestMapper {
  private static final String TEMP_FILE = "file";
  private static final String FILE_NAME = "attachment";

  public Channel toRestChannel(app.m.advise.model.Channel domain) {
    return new Channel()
        .id(domain.getId())
        .invited(domain.getInvited())
        .creator(domain.getCreator());
  }

  public app.m.advise.model.Channel toDomainChannel(Channel rest) {
    return app.m.advise.model.Channel.builder()
        .id(rest.getId())
        .creator(rest.getCreator())
        .invited(rest.getInvited())
        .build();
  }

  public Message toRestMessage(app.m.advise.model.Message domain) {
    var attachment = domain.getAttachment();
    var fileAsByte = attachment != null ? Base64.getDecoder().decode(domain.getAttachment()) : null;
    var file = fileAsByte != null ? write(fileAsByte, createTempFolder(), FILE_NAME) : null;
    return new Message()
        .id(domain.getId())
        .senderId(domain.getSenderId())
        .receiverId(domain.getReceiverId())
        .content(domain.getContent())
        .attachment(file);
  }

  @SneakyThrows
  public app.m.advise.model.Message toDomainMessage(String channelId, Message rest) {
    var attachement = rest.getAttachment();

    var fileAsString =
        attachement != null
            ? Base64.getEncoder().encodeToString(Files.readAllBytes(attachement.toPath()))
            : null;

    return app.m.advise.model.Message.builder()
        .id(rest.getId())
        .senderId(rest.getSenderId())
        .receiverId(rest.getReceiverId())
        .content(rest.getContent())
        .channelId(channelId)
        .attachment(fileAsString)
        .build();
  }

  @SneakyThrows
  public File write(byte[] bytes, @Nullable File directory, String filename) {
    File newFile = new File(directory, filename);
    Files.createDirectories(newFile.toPath().getParent());
    return Files.write(newFile.toPath(), bytes).toFile();
  }

  @SneakyThrows
  private File createTempFolder() {
    return createTempDirectory(TEMP_FILE).toFile();
  }
}
