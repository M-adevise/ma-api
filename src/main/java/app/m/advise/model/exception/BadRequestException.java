package app.m.advise.model.exception;

import static app.m.advise.model.exception.ApiException.ExceptionType.CLIENT_EXCEPTION;

public class BadRequestException extends ApiException {
  public BadRequestException(String message) {
    super(CLIENT_EXCEPTION, message);
  }
}
