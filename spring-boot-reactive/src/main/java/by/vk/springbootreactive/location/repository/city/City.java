package by.vk.springbootreactive.location.repository.city;

import by.vk.springbootreactive.location.repository.country.Country;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "a2b", name = "cities")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class City {

  @Id
  private Long id;
  private String name;
  private String code;

  @ToString.Exclude
  private Country country;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof City city)) {
      return false;
    }
    if (!Objects.equals(name, city.name)) {
      return false;
    }
    return Objects.equals(code, city.code);
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (code != null ? code.hashCode() : 0);
    return result;
  }
}
