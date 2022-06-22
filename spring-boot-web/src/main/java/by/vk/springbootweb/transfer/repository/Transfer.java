package by.vk.springbootweb.transfer.repository;

import by.vk.springbootweb.location.repository.location.Location;
import com.vladmihalcea.hibernate.type.range.Range;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

@Table(schema = "a2b", name = "transfers")
@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "origin", nullable = false)
  @ToString.Exclude
  private Location origin;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "destination", nullable = false)
  @ToString.Exclude
  private Location destination;

  private Short capacity;

  @Temporal(TemporalType.DATE)
  private Date date;

  @Column(columnDefinition = "tsrange")
  private Range<LocalDateTime> duration;

  private BigDecimal price;

  @Column(columnDefinition = "text")
  private String description;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Transfer transfer = (Transfer) o;
    return id != null && Objects.equals(id, transfer.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
