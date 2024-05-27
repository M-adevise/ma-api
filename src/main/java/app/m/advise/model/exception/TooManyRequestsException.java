package app.m.advise.model.exception;

import static app.m.advise.model.exception.ApiException.ExceptionType.CLIENT_EXCEPTION;

public class TooManyRequestsException extends ApiException {
  public TooManyRequestsException(String message) {
    super(CLIENT_EXCEPTION, message);
  }
}
