package by.vk.springbootreactive.transfer.mapper;

import by.vk.springbootreactive.location.repository.city.City;
import by.vk.springbootreactive.location.repository.country.Country;
import by.vk.springbootreactive.location.repository.location.Location;
import by.vk.springbootreactive.location.repository.location.Point;
import by.vk.springbootreactive.transfer.repository.Transfer;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.spi.Row;
import java.math.BigDecimal;
import java.util.Date;
import java.util.function.Function;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class TransferMapper implements Function<Row, Transfer> {

  private final ObjectMapper mapper;

  public TransferMapper(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  @SneakyThrows
  public Transfer apply(Row row) {

    //region origin
    var originId = row.get("transfer_origin_id", Long.class);
    var originLocation = row.get("transfer_origin_location", String.class);
    var originCityId = row.get("transfer_origin_city_id", Long.class);
    var originCityName = row.get("transfer_origin_city_name", String.class);
    var originCityCode = row.get("transfer_origin_city_code", String.class);
    var originCountryId = row.get("transfer_origin_country_id", Long.class);
    var originCountryName = row.get("transfer_origin_country_name", String.class);
    var originCountryCode = row.get("transfer_origin_country_code", String.class);

    var originCountry = new Country(originCountryId, originCountryName, originCountryCode);
    var originCity = new City(originCityId, originCityName, originCityCode, originCountry);
    var origin = new Location(originId, mapper.readValue(originLocation, Point.class), originCity);
    //endregion

    //region destination
    var destinationId = row.get("transfer_destination_id", Long.class);
    var destinationCityId = row.get("transfer_destination_city_id", Long.class);
    var destinationCityName = row.get("transfer_destination_city_name", String.class);
    var destinationCityCode = row.get("transfer_destination_city_code", String.class);
    var destinationCountryId = row.get("transfer_destination_country_id", Long.class);
    var destinationCountryName = row.get("transfer_destination_country_name", String.class);
    var destinationCountryCode = row.get("transfer_destination_country_code", String.class);
    var destinationLocation = row.get("transfer_destination_location", String.class);

    var destinationCountry = new Country(destinationCountryId, destinationCountryName,
        destinationCountryCode);
    var destinationCity = new City(destinationCityId, destinationCityName, destinationCityCode,
        destinationCountry);
    var destination = new Location(destinationId,
        mapper.readValue(destinationLocation, Point.class), destinationCity);
    //endregion

    //region transfer
    var id = row.get("id", Long.class);
    var capacity = row.get("capacity", Short.class);
    var date = row.get("date", Date.class);
    var duration = row.get("duration", String.class);
    var price = row.get("price", BigDecimal.class);
    var description = row.get("description", String.class);
    //endregion

    return new Transfer(id, origin, destination, capacity, date, duration, price, description);
  }
}
