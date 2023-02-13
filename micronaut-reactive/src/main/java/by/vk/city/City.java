package by.vk.city;

import by.vk.country.Country;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import io.micronaut.serde.annotation.Serdeable;
import java.util.Objects;

@Serdeable
@MappedEntity(value = "cities", schema = "a2b")
public class City {

  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private String code;

  @Relation(Relation.Kind.MANY_TO_ONE)
  private Country country;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

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
    if (!Objects.equals(code, city.code)) {
      return false;
    }
    return Objects.equals(country, city.country);
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (code != null ? code.hashCode() : 0);
    result = 31 * result + (country != null ? country.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "City{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", code='" + code + '\'' +
        '}';
  }
}
