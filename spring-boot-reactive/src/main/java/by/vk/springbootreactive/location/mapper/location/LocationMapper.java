package by.vk.springbootreactive.location.mapper.location;

import by.vk.springbootreactive.location.repository.city.City;
import by.vk.springbootreactive.location.repository.country.Country;
import by.vk.springbootreactive.location.repository.location.Location;
import by.vk.springbootreactive.location.repository.location.Point;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.util.function.BiFunction;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper implements BiFunction<Row, RowMetadata, Location> {

  private final ObjectMapper mapper;

  public LocationMapper(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  @SneakyThrows
  public Location apply(Row row, RowMetadata meta) {

    var id = row.get("id", Long.class);
    var location = row.get("location", String.class);

    var point = mapper.readValue(location, Point.class);

    var cityId = row.get("city_id", Long.class);
    var cityName = row.get("city_name", String.class);
    var cityCode = row.get("city_code", String.class);

    var countryId = row.get("country_id", Long.class);
    var countryName = row.get("country_name", String.class);
    var countryCode = row.get("country_code", String.class);

    var country = new Country(countryId, countryName, countryCode);
    var city = new City(cityId, cityName, cityCode, country);

    return new Location(id, point, city);
  }
}
