package app.m.advise.model.exception;

import static app.m.advise.model.exception.ApiException.ExceptionType.CLIENT_EXCEPTION;

public class NotFoundException extends ApiException {
  public NotFoundException(String message) {
    super(CLIENT_EXCEPTION, message);
  }
}
