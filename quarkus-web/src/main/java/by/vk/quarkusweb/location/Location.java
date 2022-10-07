package by.vk.quarkusweb.location;

import by.vk.quarkusweb.city.City;
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
import lombok.SneakyThrows;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {

  private static final String LOCATIONS_BY_CITY_QUERY_VALUE = "select l.id as id, l.location as location, l.city_id as city_id, ci.name as city_name, ci.code as city_code, co.id as country_id, co.name as country_name, co.code as country_code from a2b.locations l join a2b.cities ci on ci.id = l.city_id join a2b.countries co on co.id = ci.country_id where l.city_id = $1";

  private Long id;
  private Point location;
  private City city;

  static Multi<Location> all(PgPool client, Long city) {
    return client.preparedQuery(LOCATIONS_BY_CITY_QUERY_VALUE).execute(Tuple.of(city)).onItem()
                 .transformToMulti(rowSet -> Multi.createFrom().iterable(rowSet)).onItem()
                 .transform(Location::from);
  }

  @SneakyThrows
  static Location from(Row row) {
    var id = row.getLong("id");
    var location = row.getJsonObject("location");

    var point = location.mapTo(Point.class);

    var cityId = row.getLong("city_id");
    var cityName = row.getString("city_name");
    var cityCode = row.getString("city_code");

    var countryId = row.getLong("country_id");
    var countryName = row.getString("country_name");
    var countryCode = row.getString("country_code");

    var country = new Country(countryId, countryName, countryCode);
    var city = new City(cityId, cityName, cityCode, country);

    return new Location(id, point, city);
  }

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
