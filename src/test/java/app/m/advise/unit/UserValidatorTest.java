package app.m.advise.unit;

import static app.m.advise.testutils.TestUtils.user1;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import app.m.advise.endpoint.rest.model.User;
import app.m.advise.endpoint.validator.UserValidator;
import app.m.advise.model.exception.BadRequestException;
import org.junit.jupiter.api.Test;

class UserValidatorTest {
  private UserValidator subject = new UserValidator();

  @Test
  void validate_user_ok() {
    assertDoesNotThrow(() -> subject.accept(user1()));
  }

  @Test
  void validate_user_ko() {
    assertThrows(BadRequestException.class, () -> subject.accept(unCompletUser()));
  }

  public User unCompletUser() {
    return new User().lastName("test").firstName("test");
  }
}
