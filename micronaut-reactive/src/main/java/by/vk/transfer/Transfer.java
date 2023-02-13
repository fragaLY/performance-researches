package by.vk.transfer;

import by.vk.location.Location;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;
import io.micronaut.serde.annotation.Serdeable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;


@Serdeable
@MappedEntity(value = "transfers", schema = "a2b")
public class Transfer {

  @Id
  @GeneratedValue
  private Long id;

  @Relation(Relation.Kind.MANY_TO_ONE)
  private Location origin;

  @Relation(Relation.Kind.MANY_TO_ONE)
  private Location destination;

  private Short capacity;

  @TypeDef(type = DataType.DATE)
  private Date date;

  private String duration;

  private BigDecimal price;

  private String description;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Location getOrigin() {
    return origin;
  }

  public void setOrigin(Location origin) {
    this.origin = origin;
  }

  public Location getDestination() {
    return destination;
  }

  public void setDestination(Location destination) {
    this.destination = destination;
  }

  public Short getCapacity() {
    return capacity;
  }

  public void setCapacity(Short capacity) {
    this.capacity = capacity;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Transfer transfer)) {
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
    int result = capacity != null ? capacity.hashCode() : 0;
    result = 31 * result + (date != null ? date.hashCode() : 0);
    result = 31 * result + (duration != null ? duration.hashCode() : 0);
    result = 31 * result + (price != null ? price.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Transfer{" +
        "capacity=" + capacity +
        ", date=" + date +
        ", duration='" + duration + '\'' +
        ", price=" + price +
        ", description='" + description + '\'' +
        '}';
  }
}
