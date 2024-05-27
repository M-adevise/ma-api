package app.m.advise.model.exception;

import static app.m.advise.model.exception.ApiException.ExceptionType.SERVER_EXCEPTION;

public class NotImplementedException extends ApiException {
  public NotImplementedException(String message) {
    super(SERVER_EXCEPTION, message);
  }
}
