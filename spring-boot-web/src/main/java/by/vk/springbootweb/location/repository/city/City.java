package by.vk.springbootweb.location.repository.city;

import by.vk.springbootweb.location.repository.country.Country;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
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

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "country_id", nullable = false)
  @ToString.Exclude
  private Country country;

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
