package by.vk.quarkusweb.city;

import by.vk.quarkusweb.country.Country;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class City {

  private static final String GET_ALL_CITIES_QUERY = "select ci.id as id, ci.name as name, ci.code as code, co.id as country_id, co.name as country_name, co.code as country_code from a2b.cities ci join a2b.countries co on ci.country_id = co.id where ci.country_id = $1";

  private Long id;
  private String name;
  private String code;
  private Country country;

  static City from(Row row) {
    return new City(row.getLong("id"), row.getString("name"), row.getString("code"),
        new Country(row.getLong("country_id"), row.getString("country_name"),
            row.getString("country_code")));
  }

  static Multi<City> all(PgPool client, Long country) {
    return client.preparedQuery(GET_ALL_CITIES_QUERY).execute(Tuple.of(country))
                 .onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
                 .onItem().transform(City::from);
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
    return Objects.equals(code, city.code);
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (code != null ? code.hashCode() : 0);
    return result;
  }
}
