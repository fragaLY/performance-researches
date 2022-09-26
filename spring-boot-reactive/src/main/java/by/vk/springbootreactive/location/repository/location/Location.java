package by.vk.springbootreactive.location.repository.location;

import by.vk.springbootreactive.location.repository.city.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "a2b", name = "locations")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {

  @Id
  private Long id;

  private Point location;

  @ToString.Exclude
  private City city;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Location location1)) {
      return false;
    }
    return location.equals(location1.location);
  }

  @Override
  public int hashCode() {
    return location.hashCode();
  }
}
