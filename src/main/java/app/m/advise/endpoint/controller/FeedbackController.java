package app.m.advise.endpoint.controller;

import app.m.advise.endpoint.mapper.FeedbackRestMapper;
import app.m.advise.endpoint.rest.model.Feedback;
import app.m.advise.endpoint.rest.model.FeedbackSummary;
import app.m.advise.service.DoctorService;
import app.m.advise.service.FeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class FeedbackController {
  private final FeedbackService service;
  private final FeedbackRestMapper mapper;
  private final DoctorService doctorService;

  @GetMapping("/doctors/{id}/feedbacks")
  public FeedbackSummary getDoctorFeedbacks(@PathVariable("id") String id) {
    var feedbacks = service.getDoctorsFeedbacks(id).stream().map(mapper::toRest).toList();
    return mapper.toSummary(feedbacks);
  }

  @PutMapping("/doctors/{id}/feedbacks")
  public Feedback giveFeedbacks(@PathVariable("id") String id, @RequestBody Feedback feedback) {
    var doctor = doctorService.getDoctorById(id);
    var toSave = mapper.toDomain(feedback, doctor);
    return mapper.toRest(service.crupdate(toSave));
  }
}
