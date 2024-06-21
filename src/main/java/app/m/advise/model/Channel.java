package app.m.advise.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"channel\"")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Channel implements Serializable {
  @Id private String id;
  private String creator;
  private String invited;
}
