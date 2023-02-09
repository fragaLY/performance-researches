package by.vk.springbootreactive.location.mapper.city;

import by.vk.springbootreactive.location.repository.city.City;
import by.vk.springbootreactive.location.repository.country.Country;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.util.function.BiFunction;
import org.springframework.stereotype.Component;

@Component
public class CityMapper implements BiFunction<Row, RowMetadata, City> {

  @Override
  public City apply(Row row, RowMetadata meta) {

    var id = row.get("id", Long.class);
    var name = row.get("name", String.class);
    var code = row.get("code", String.class);

    var countryId = row.get("country_id", Long.class);
    var countryName = row.get("country_name", String.class);
    var countryCode = row.get("country_code", String.class);

    var country = new Country(countryId, countryName, countryCode);

    return new City(id, name, code, country);
  }
}
