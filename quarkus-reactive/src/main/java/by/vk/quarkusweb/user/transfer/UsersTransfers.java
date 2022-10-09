package by.vk.quarkusweb.user.transfer;

import by.vk.quarkusweb.city.City;
import by.vk.quarkusweb.country.Country;
import by.vk.quarkusweb.location.Location;
import by.vk.quarkusweb.location.Point;
import by.vk.quarkusweb.transfer.Transfer;
import by.vk.quarkusweb.user.User;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
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
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersTransfers {

  private static final String USER_TRANSFERS_BY_USER_QUERY_VALUE =
      "select u.id as user_id, u.first_name as user_first_name, u.last_name as user_last_name, u.email as user_email, u.phone as user_phone,\n"
          + "       t.id as transfer_id, t.origin as transfer_origin_id, t.destination as transfer_destination_id, t.capacity as transfer_capacity, t.date as transfer_date, t.duration as transfer_duration, t.price::numeric  as transfer_price, t.description as transfer_description,\n"
          + "       o.id as transfer_origin_id, o.location as transfer_origin_location, d.id as transfer_destination_id, d.location as transfer_destination_location,\n"
          + "       dc.id as transfer_destination_city_id, dc.name as transfer_destination_city_name, dc.code as transfer_destination_city_code,\n"
          + "       oc.id as transfer_origin_city_id, oc.name as transfer_origin_city_name, oc.code as transfer_origin_city_code,\n"
          + "       oco.id as transfer_origin_country_id, oco.name as transfer_origin_country_name, oco.code as transfer_origin_country_code,\n"
          + "       dco.id as transfer_destination_country_id, dco.name as transfer_destination_country_name, dco.code as transfer_destination_country_code\n,"
          + "       ut.state as state,  ut.description as description\n"
          + "from a2b.users_transfers ut\n" + "join a2b.users u on ut.user_id = u.id\n"
          + "join a2b.transfers t on t.id = ut.transfer_id\n"
          + "left join a2b.locations o on t.origin = o.id\n"
          + "left join a2b.locations d on t.destination = d.id\n"
          + "left join a2b.cities oc on oc.id = o.city_id\n"
          + "left join a2b.cities dc on dc.id = d.city_id\n"
          + "left join a2b.countries oco on oco.id = oc.country_id\n"
          + "left join a2b.countries dco on dco.id = dc.country_id\n" + "where user_id = $1";

  private static final String CREATE_USER_TRANSFER_QUERY_VALUE = "INSERT INTO a2b.users_transfers (user_id, transfer_id, state, description) VALUES ($1, $2, $3, $4)";
  private static final String UPDATE_USER_TRANSFER_QUERY_VALUE = "UPDATE a2b.users_transfers SET state = $1, description = $2 WHERE user_id = $3 AND transfer_id = $4";

  private UsersTransfersId id;
  private User user;
  private Transfer transfer;
  private State state;
  private String description;

  static UsersTransfers from(Row row) {
    //region user
    var userId = row.getLong("user_id");
    var firstName = row.getString("user_first_name");
    var lastName = row.getString("user_last_name");
    var email = row.getString("user_email");
    var phone = row.getString("user_phone");

    var user = new User(userId, firstName, lastName, email, phone, null);
    //endregion

    var transferId = row.getLong("transfer_id");

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
    var capacity = row.getShort("transfer_capacity");
    var date = row.getLocalDate("transfer_date");
    var duration = row.getString("transfer_duration");
    var price = row.getBigDecimal("transfer_price");
    var transferDescription = row.getString("transfer_description");
    var transfer = new Transfer(transferId, origin, destination, capacity, date, duration, price,
        transferDescription);
    //endregion

    //region users transfers
    var id = new UsersTransfersId(userId, transferId);
    var state = State.valueOf(row.getString("state"));
    var description = row.getString("description");
    //endregion
    return new UsersTransfers(id, user, transfer, state, description);
  }

  public static Uni<UsersTransfers> transfer(PgPool client, Long userId, Long transferId) {
    var sql = USER_TRANSFERS_BY_USER_QUERY_VALUE + " AND transfer_id = $2";
    return client.preparedQuery(sql).execute(Tuple.from(List.of(userId, transferId)))
                 .onItem().transform(RowSet::iterator)
                 .onItem().transform(it -> it.hasNext() ? from(it.next()) : null);
  }

  public static Multi<UsersTransfers> transfers(PgPool client, Long userId) {
    return client.preparedQuery(USER_TRANSFERS_BY_USER_QUERY_VALUE).execute(Tuple.of(userId))
                 .onItem().transformToMulti(it -> Multi.createFrom().iterable(it))
                 .onItem().transform(UsersTransfers::from);
  }

  public static Uni<Integer> update(PgPool client, Long userId, Long transferId,
      UserTransferEditionPayload payload) {
    return client.preparedQuery(UPDATE_USER_TRANSFER_QUERY_VALUE).execute(
                     Tuple.from(List.of(payload.state(), payload.description(), userId, transferId)))
                 .onItem()
                 .transform(RowSet::rowCount);
  }

  public static Uni<Integer> create(PgPool client, Long userId, Long transferId,
      UserTransferCreationPayload payload) {
    return client.preparedQuery(CREATE_USER_TRANSFER_QUERY_VALUE).execute(
                     Tuple.from(List.of(userId, transferId, State.BOOKED, payload.description())))
                 .onItem()
                 .transform(RowSet::rowCount);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UsersTransfers that)) {
      return false;
    }

    if (!Objects.equals(user, that.user)) {
      return false;
    }
    if (!Objects.equals(transfer, that.transfer)) {
      return false;
    }
    if (state != that.state) {
      return false;
    }
    return Objects.equals(description, that.description);
  }

  @Override
  public int hashCode() {
    int result = user != null ? user.hashCode() : 0;
    result = 31 * result + (transfer != null ? transfer.hashCode() : 0);
    result = 31 * result + (state != null ? state.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    return result;
  }
}
