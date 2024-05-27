package app.m.advise.unit;

import static app.m.advise.testutils.TestUtils.travelDescription;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import app.m.advise.endpoint.rest.model.TravelDescription;
import app.m.advise.endpoint.validator.TravelValidator;
import app.m.advise.model.exception.BadRequestException;
import org.junit.jupiter.api.Test;

class TravelValidatorTest {
  private final TravelValidator subject = new TravelValidator();

  @Test
  void validate_travel_ok() {
    assertDoesNotThrow(() -> subject.accept(travelDescription()));
  }

  @Test
  void validate_travel_ko() {
    assertThrows(BadRequestException.class, () -> subject.accept(unCompletDescription()));
  }

  public TravelDescription unCompletDescription() {
    return new TravelDescription().from("New York").to("Paris");
  }
}
