package by.vk.datagen.data.location;

import by.vk.datagen.data.city.City;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;

@Table(schema = "a2b", name = "locations")
@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "jsonb")
  @Type(type = "jsonb")
  private Point location;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "city_id", nullable = false)
  @ToString.Exclude
  private City city;

  public Location(Long id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Location location = (Location) o;
    return id != null && Objects.equals(id, location.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
