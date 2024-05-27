package app.m.advise.endpoint.controller;

import app.m.advise.endpoint.mapper.RouteRestMapper;
import app.m.advise.endpoint.rest.model.Itinerary;
import app.m.advise.endpoint.rest.model.TravelDescription;
import app.m.advise.endpoint.validator.TravelValidator;
import app.m.advise.service.RouteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RouteController {
  private final RouteService service;
  private final RouteRestMapper mapper;
  private final TravelValidator validator;

  @PostMapping(value = "/itineraries")
  public Itinerary generateItineraries(@RequestBody TravelDescription travelDescription) {
    validator.accept(travelDescription);
    var transport = mapper.toDomainTravelPayload(travelDescription);
    var accommodation = mapper.toDomainAccommodationPayload(travelDescription);
    return service.generateItineraries(transport, accommodation, travelDescription);
  }
}
