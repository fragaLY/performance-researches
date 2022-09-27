package by.vk.springbootreactivenative.location.repository.location;

import by.vk.springbootreactivenative.location.repository.city.City;
import java.util.Objects;
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

    if (!Objects.equals(location, location1.location)) {
      return false;
    }
    return Objects.equals(city, location1.city);
  }

  @Override
  public int hashCode() {
    int result = location != null ? location.hashCode() : 0;
    result = 31 * result + (city != null ? city.hashCode() : 0);
    return result;
  }
}
