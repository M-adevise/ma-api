package app.m.advise.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "\"feedback\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback implements Serializable {
  @Id private String id;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "sender")
  private Patient sender;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "receiver")
  private Doctor receiver;

  private String comment;
  private int score;
  @CreationTimestamp private Instant creationDatetime;
}
