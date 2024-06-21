package app.m.advise.service;

import app.m.advise.model.Channel;
import app.m.advise.model.Message;
import app.m.advise.repository.ChannelRepository;
import app.m.advise.repository.MessageRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommunicationService {
  private final ChannelRepository repository;
  private final MessageRepository messageRepository;

  public Channel crupdate(Channel toSave) {
    return repository.save(toSave);
  }

  public List<Channel> getChannels(String userId) {
    return repository.findByCreatorOrInvited(userId, userId);
  }

  public Message crupdateMessage(Message message) {
    return messageRepository.save(message);
  }

  public List<Message> getMessages(String cId, Integer page, Integer pageSize) {
    int pageValue = page == null ? 0 : page - 1;
    int pageSizeValue = page == null ? 20 : pageSize;
    Pageable pageable = PageRequest.of(pageValue, pageSizeValue);
    return messageRepository.findByChannelId(cId, pageable);
  }
}
