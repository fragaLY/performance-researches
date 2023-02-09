package by.vk.springbootreactive.user.mapper;

import by.vk.springbootreactive.location.repository.city.City;
import by.vk.springbootreactive.location.repository.country.Country;
import by.vk.springbootreactive.location.repository.location.Location;
import by.vk.springbootreactive.location.repository.location.Point;
import by.vk.springbootreactive.transfer.repository.Transfer;
import by.vk.springbootreactive.user.repository.user.User;
import by.vk.springbootreactive.user.repository.usertransfer.State;
import by.vk.springbootreactive.user.repository.usertransfer.UsersTransfers;
import by.vk.springbootreactive.user.repository.usertransfer.UsersTransfersId;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.math.BigDecimal;
import java.util.Date;
import java.util.function.BiFunction;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class UserTransfersMapper implements BiFunction<Row, RowMetadata, UsersTransfers> {

  private final ObjectMapper mapper;

  public UserTransfersMapper(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  @SneakyThrows
  public UsersTransfers apply(Row row, RowMetadata meta) {

    //region user
    var userId = row.get("user_id", Long.class);
    var firstName = row.get("user_first_name", String.class);
    var lastName = row.get("user_last_name", String.class);
    var email = row.get("user_email", String.class);
    var phone = row.get("user_phone", String.class);

    var user = new User(userId, firstName, lastName, email, phone, null);
    //endregion

    var transferId = row.get("transfer_id", Long.class);

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
    var capacity = row.get("transfer_capacity", Short.class);
    var date = row.get("transfer_date", Date.class);
    var duration = row.get("transfer_duration", String.class);
    var price = row.get("transfer_price", BigDecimal.class);
    var transferDescription = row.get("transfer_description", String.class);
    var transfer = new Transfer(transferId, origin, destination, capacity, date, duration, price,
        transferDescription);
    //endregion

    //region users transfers
    var id = new UsersTransfersId(userId, transferId);
    var state = State.valueOf(row.get("state", String.class));
    var description = row.get("description", String.class);
    //endregion
    return new UsersTransfers(id, user, transfer, state, description);
  }
}
