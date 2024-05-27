package app.m.advise.endpoint.controller;

import app.m.advise.endpoint.rest.model.InlineObject;
import app.m.advise.endpoint.rest.model.Weather;
import app.m.advise.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WeatherController {
  private final WeatherService service;

  @PostMapping("/weathers")
  public Weather getForecasts(@RequestBody InlineObject origDest) {
    return service.getForecast(origDest);
  }
}
