package by.vk.quarkusweb.transfer;

import by.vk.quarkusweb.city.City;
import by.vk.quarkusweb.country.Country;
import by.vk.quarkusweb.location.Location;
import by.vk.quarkusweb.location.Point;
import by.vk.quarkusweb.user.User;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"origin", "destination"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {

  private static final String TRANSFERS_BY_ORIGIN_AND_DESTINATION_AND_DATE =
      "select t.id as id, t.origin as transfer_origin_id, t.destination as transfer_destination_id, t.capacity as capacity, t.date as date, t.duration as duration, t.price::numeric as price, t.description as description,\n"
          + "       o.id as transfer_origin_id, o.location as transfer_origin_location, d.id as transfer_destination_id, d.location as transfer_destination_location,\n"
          + "       dc.id as transfer_destination_city_id, dc.name as transfer_destination_city_name, dc.code as transfer_destination_city_code,\n"
          + "       oc.id as transfer_origin_city_id, oc.name as transfer_origin_city_name, oc.code as transfer_origin_city_code,\n"
          + "       oco.id as transfer_origin_country_id, oco.name as transfer_origin_country_name, oco.code as transfer_origin_country_code,\n"
          + "       dco.id as transfer_destination_country_id, dco.name as transfer_destination_country_name, dco.code as transfer_destination_country_code\n"
          + "from a2b.transfers t\n" + "left join a2b.locations o on t.origin = o.id\n"
          + "left join a2b.locations d on t.destination = d.id\n"
          + "left join a2b.cities oc on oc.id = o.city_id\n"
          + "left join a2b.cities dc on dc.id = d.city_id\n"
          + "left join a2b.countries oco on oco.id = oc.country_id\n"
          + "left join a2b.countries dco on dco.id = dc.country_id\n" + "where origin = $1\n"
          + "and destination = $2\n" + "and date = $3";

  private Long id;
  private Location origin;
  private Location destination;
  private Short capacity;
  private LocalDate date;
  private String duration;
  private BigDecimal price;
  private String description;

  static Multi<Transfer> all(PgPool client, Long originId, Long destinationId, LocalDate date) {
    return client.preparedQuery(TRANSFERS_BY_ORIGIN_AND_DESTINATION_AND_DATE)
                 .execute(Tuple.from(List.of(originId, destinationId, date)))
                 .onItem()
                 .transformToMulti(rowSet -> Multi.createFrom().iterable(rowSet)).onItem()
                 .transform(Transfer::from);
  }

  static Transfer from(Row row) {
    //region origin
    var originId = row.getLong("transfer_origin_id");
    var originLocation = row.getJsonObject("transfer_origin_location");
    var originCityId = row.getLong("transfer_origin_city_id");
    var originCityName = row.getString("transfer_origin_city_name");
    var originCityCode = row.getString("transfer_origin_city_code");
    var originCountryId = row.getLong("transfer_origin_country_id");
    var originCountryName = row.getString("transfer_origin_country_name");
    var originCountryCode = row.getString("transfer_origin_country_code");

    var originCountry = new Country(originCountryId, originCountryName, originCountryCode);
    var originCity = new City(originCityId, originCityName, originCityCode, originCountry);
    var origin = new Location(originId, originLocation.mapTo(Point.class), originCity);
    //endregion

    //region destination
    var destinationId = row.getLong("transfer_destination_id");
    var destinationCityId = row.getLong("transfer_destination_city_id");
    var destinationCityName = row.getString("transfer_destination_city_name");
    var destinationCityCode = row.getString("transfer_destination_city_code");
    var destinationCountryId = row.getLong("transfer_destination_country_id");
    var destinationCountryName = row.getString("transfer_destination_country_name");
    var destinationCountryCode = row.getString("transfer_destination_country_code");
    var destinationLocation = row.getJsonObject("transfer_destination_location");

    var destinationCountry = new Country(destinationCountryId, destinationCountryName,
        destinationCountryCode);
    var destinationCity = new City(destinationCityId, destinationCityName, destinationCityCode,
        destinationCountry);
    var destination = new Location(destinationId, destinationLocation.mapTo(Point.class),
        destinationCity);
    //endregion

    //region transfer
    var id = row.getLong("id");
    var capacity = row.getShort("capacity");
    var date = row.getLocalDate("date");
    var duration = row.getString("duration");
    var price = row.getBigDecimal("price");
    var description = row.getString("description");
    //endregion

    return new Transfer(id, origin, destination, capacity, date, duration, price, description);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Transfer transfer)) {
      return false;
    }

    if (!Objects.equals(origin, transfer.origin)) {
      return false;
    }
    if (!Objects.equals(destination, transfer.destination)) {
      return false;
    }
    if (!Objects.equals(capacity, transfer.capacity)) {
      return false;
    }
    if (!Objects.equals(date, transfer.date)) {
      return false;
    }
    if (!Objects.equals(duration, transfer.duration)) {
      return false;
    }
    if (!Objects.equals(price, transfer.price)) {
      return false;
    }
    return Objects.equals(description, transfer.description);
  }

  @Override
  public int hashCode() {
    int result = origin != null ? origin.hashCode() : 0;
    result = 31 * result + (destination != null ? destination.hashCode() : 0);
    result = 31 * result + (capacity != null ? capacity.hashCode() : 0);
    result = 31 * result + (date != null ? date.hashCode() : 0);
    result = 31 * result + (duration != null ? duration.hashCode() : 0);
    result = 31 * result + (price != null ? price.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    return result;
  }
}
