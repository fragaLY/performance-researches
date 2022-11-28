package by.vk.springbootweb.transfer.repository;

import by.vk.springbootweb.location.repository.location.Location;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

  private String duration;

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
