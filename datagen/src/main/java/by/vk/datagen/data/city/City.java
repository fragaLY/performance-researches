package by.vk.datagen.data.city;

import by.vk.datagen.data.country.Country;
import java.util.Objects;
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

@Table(schema = "a2b", name = "cities")
@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class City {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String code;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "country_id", nullable = false)
  @ToString.Exclude
  private Country country;

  public City(Long id) {
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
    City city = (City) o;
    return id != null && Objects.equals(id, city.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
