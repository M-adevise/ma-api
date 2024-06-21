package app.m.advise.endpoint.controller;

import app.m.advise.endpoint.mapper.CommunicationRestMapper;
import app.m.advise.endpoint.rest.model.Channel;
import app.m.advise.endpoint.rest.model.Message;
import app.m.advise.service.CommunicationService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CommunicationController {
  private final CommunicationService service;
  private final CommunicationRestMapper mapper;

  @PutMapping("/channel/{id}")
  public Channel crupdate(@PathVariable("id") String id, @RequestBody Channel body) {
    var toSave = mapper.toDomainChannel(body);
    return mapper.toRestChannel(service.crupdate(toSave));
  }

  @PutMapping("/channels/{id}/messages/{mId}")
  public Message crupdate(
      @PathVariable("id") String id, @PathVariable("mId") String mId, @RequestBody Message body) {
    var toSave = mapper.toDomainMessage(id, body);
    return mapper.toRestMessage(service.crupdateMessage(toSave));
  }

  @GetMapping("/users/{id}/channels")
  public List<Channel> getChannels(@PathVariable("id") String id) {
    return service.getChannels(id).stream().map(mapper::toRestChannel).toList();
  }

  @GetMapping("/channels/{id}/messages")
  public List<Message> getChannels(
      @PathVariable("id") String id,
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "pageSize", required = false) Integer pageSize) {
    return service.getMessages(id, page, pageSize).stream().map(mapper::toRestMessage).toList();
  }
}
