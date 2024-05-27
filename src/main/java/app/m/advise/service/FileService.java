package app.m.advise.service;

import static app.m.advise.model.exception.ApiException.ExceptionType.SERVER_EXCEPTION;

import app.m.advise.endpoint.security.AuthProvider;
import app.m.advise.model.exception.ApiException;
import app.m.advise.repository.UserRepository;
import app.m.advise.service.file.FileStorageService;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FileService {
  private final FileStorageService fileStorageService;
  private final UserRepository userRepository;

  public String uploadFile(String fileId, byte[] file) {
    try {
      return fileStorageService.uploadFile(fileId, file);
    } catch (IOException e) {
      throw new ApiException(SERVER_EXCEPTION, e.getMessage());
    }
  }

  public String updateUserPhoto(String fileId, byte[] file) {
    var authUser = AuthProvider.getUser();
    userRepository.save(authUser.toBuilder().photoId(fileId).build());
    try {
      return fileStorageService.uploadFile(fileId, file);
    } catch (IOException e) {
      throw new ApiException(SERVER_EXCEPTION, e.getMessage());
    }
  }

  public byte[] downloadFile(String fileId) {
    return fileStorageService.downloadFile(fileId);
  }
}
