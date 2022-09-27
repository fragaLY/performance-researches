package by.vk.springbootreactivenative.transfer.repository;

import by.vk.springbootreactivenative.location.repository.location.Location;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "a2b", name = "transfers")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {

  @Id
  private Long id;
  @ToString.Exclude
  private Location origin;
  @ToString.Exclude
  private Location destination;
  private Short capacity;
  private Date date;
  private String duration;
  private BigDecimal price;
  private String description;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Transfer transfer)) {
      return false;
    }

    if (!Objects.equals(origin, transfer.origin)) {
      return false;
    }
    if (!Objects.equals(destination, transfer.destination)) {
      return false;
    }
    if (!Objects.equals(capacity, transfer.capacity)) {
      return false;
    }
    if (!Objects.equals(date, transfer.date)) {
      return false;
    }
    if (!Objects.equals(duration, transfer.duration)) {
      return false;
    }
    if (!Objects.equals(price, transfer.price)) {
      return false;
    }
    return Objects.equals(description, transfer.description);
  }

  @Override
  public int hashCode() {
    int result = origin != null ? origin.hashCode() : 0;
    result = 31 * result + (destination != null ? destination.hashCode() : 0);
    result = 31 * result + (capacity != null ? capacity.hashCode() : 0);
    result = 31 * result + (date != null ? date.hashCode() : 0);
    result = 31 * result + (duration != null ? duration.hashCode() : 0);
    result = 31 * result + (price != null ? price.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    return result;
  }
}
