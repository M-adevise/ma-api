package app.m.advise.endpoint.validator;

import app.m.advise.endpoint.rest.model.User;
import app.m.advise.model.exception.BadRequestException;
import java.util.function.Consumer;
import org.springframework.stereotype.Component;

@Component
public class UserValidator implements Consumer<User> {
  @Override
  public void accept(User user) {
    var builder = new StringBuilder();
    if (user.getId() == null) {
      builder.append("id is mandatory. ");
    }
    if (user.getNic() == null) {
      builder.append("NIC is mandatory");
    }
    if (user.getEmail() == null) {
      builder.append("email is mandatory. ");
    }
    if (user.getAuthenticationId() == null) {
      builder.append("AuthenticationId is mandatory. ");
    }

    if (!builder.isEmpty()) {
      throw new BadRequestException(builder.toString());
    }
  }
}
