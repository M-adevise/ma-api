package app.m.advise.repository;

import app.m.advise.model.Channel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, String> {
  List<Channel> findByCreatorOrInvited(String creator, String invited);
}
