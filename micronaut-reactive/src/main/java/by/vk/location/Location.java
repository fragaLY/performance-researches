package by.vk.location;

import by.vk.city.City;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
@MappedEntity(value = "locations", schema = "a2b")
public class Location {

  @Id
  @GeneratedValue
  private Long id;

  private String location;

  @Relation(Relation.Kind.MANY_TO_ONE)
  private City city;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Location location1)) {
      return false;
    }

    if (!location.equals(location1.location)) {
      return false;
    }
    return city.equals(location1.city);
  }

  @Override
  public int hashCode() {
    int result = location.hashCode();
    result = 31 * result + city.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Location{" +
        "point=" + location +
        '}';
  }
}

