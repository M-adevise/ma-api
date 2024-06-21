package app.m.advise.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"message\"")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Message implements Serializable {
  @Id private String id;
  private String senderId;
  private String receiverId;
  private String channelId;
  private String content;

  @Column(columnDefinition = "text")
  private String attachment;
}
