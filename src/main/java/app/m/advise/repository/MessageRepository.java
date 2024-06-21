package app.m.advise.repository;

import app.m.advise.model.Message;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {
  List<Message> findByChannelId(String channelId, Pageable pageable);
}
