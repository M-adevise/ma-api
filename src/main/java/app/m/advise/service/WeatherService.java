package app.m.advise.service;

import app.m.advise.endpoint.rest.model.Forecast;
import app.m.advise.endpoint.rest.model.ForecastDay;
import app.m.advise.endpoint.rest.model.ForecastDayDay;
import app.m.advise.endpoint.rest.model.ForecastDayDayCondition;
import app.m.advise.endpoint.rest.model.ForecastLocation;
import app.m.advise.endpoint.rest.model.InlineObject;
import app.m.advise.endpoint.rest.model.Weather;
import app.m.advise.service.api.weather.WeatherApi;
import app.m.advise.service.api.weather.payload.WeatherResponse;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WeatherService {
  private final WeatherApi weatherApi;

  public Weather getForecast(InlineObject origDest) {
    WeatherResponse origin = weatherApi.getForecasts(origDest.getOrigin());
    WeatherResponse destination = weatherApi.getForecasts(origDest.getDestination());
    return new Weather()
        .origin(
            new Forecast()
                .location(
                    new ForecastLocation()
                        .name(origin.getLocation().getName())
                        .country(origin.getLocation().getCountry())
                        .region(origin.getLocation().getRegion()))
                .forecast(forecastDayList(origin)))
        .destination(
            new Forecast()
                .location(
                    new ForecastLocation()
                        .name(destination.getLocation().getName())
                        .country(destination.getLocation().getCountry())
                        .region(destination.getLocation().getRegion()))
                .forecast(forecastDayList(destination)));
  }

  private List<ForecastDay> forecastDayList(WeatherResponse weatherResponse) {
    return weatherResponse.getForecast().getForecastDay().stream().map(this::forecastDay).toList();
  }

  private ForecastDay forecastDay(WeatherResponse.ForecastDay forecastDay) {
    return new ForecastDay()
        .date(forecastDay.getDate())
        .day(
            new ForecastDayDay()
                .maxTemp(new BigDecimal(forecastDay.getDay().getMaxTempC()))
                .minTemp(new BigDecimal(forecastDay.getDay().getMinTempC()))
                .condition(
                    new ForecastDayDayCondition()
                        .text(forecastDay.getDay().getCondition().getText())
                        .icon(forecastDay.getDay().getCondition().getIcon())));
  }
}
