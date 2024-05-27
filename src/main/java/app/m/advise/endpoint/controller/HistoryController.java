package app.m.advise.endpoint.controller;

import app.m.advise.endpoint.rest.model.Itinerary;
import app.m.advise.model.History;
import app.m.advise.service.HistoryService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class HistoryController {
  private final HistoryService service;

  @GetMapping("/activities")
  public List<Itinerary> getActivities() {
    return service.getHistory().stream().map(History::getItinerary).collect(Collectors.toList());
  }
}
