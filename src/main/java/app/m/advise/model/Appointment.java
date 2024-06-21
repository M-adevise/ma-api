package app.m.advise.model;

import static jakarta.persistence.CascadeType.ALL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "\"appointment\"")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment implements Serializable {
  @Id private String id;
  private String summary;

  @ManyToOne(cascade = ALL)
  @JoinColumn(name = "organizer")
  private Doctor organizer;

  private String roomId;

  @ManyToOne(cascade = ALL)
  @JoinColumn(name = "participant")
  private Patient participant;

  @Timestamp
  @Column(name = "beginning")
  private Instant from;

  @Timestamp
  @Column(name = "ending")
  private Instant to;

  @CreationTimestamp private Instant creationDatetime;
}
