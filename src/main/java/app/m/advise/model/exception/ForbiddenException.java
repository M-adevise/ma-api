package app.m.advise.model.exception;

import static app.m.advise.model.exception.ApiException.ExceptionType.CLIENT_EXCEPTION;

public class ForbiddenException extends ApiException {
  public ForbiddenException(String message) {
    super(CLIENT_EXCEPTION, message);
  }
}
