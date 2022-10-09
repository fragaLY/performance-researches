package by.vk.quarkusweb.user;

import by.vk.quarkusweb.user.transfer.UsersTransfers;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString(exclude = "transfers")
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {

  private static final String USER_BY_ID = "select u.id as id , u.first_name as firstName, u.last_name as lastName, u.email as email, u.phone as phone from a2b.users u where u.id = $1";
  private static final String UPDATE_USER_QUERY_VALUE = "update a2b.users set first_name = $1, last_name = $2 where id = $3";


  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private Set<UsersTransfers> transfers;

  static Uni<User> one(PgPool client, Long userId) {
    return client.preparedQuery(USER_BY_ID).execute(Tuple.of(userId))
                 .onItem().transform(RowSet::iterator)
                 .onItem().transform(iterator -> iterator.hasNext() ? from(iterator.next()) : null);
  }

  public static Uni<Integer> update(PgPool client, Long userId, UserEditionPayload payload) {
    return client.preparedQuery(UPDATE_USER_QUERY_VALUE)
                 .execute(Tuple.from(List.of(payload.firstName(), payload.lastName(), userId)))
                 .onItem().transform(RowSet::rowCount);
  }

  static User from(Row row) {
    var id = row.getLong("id");
    var fistName = row.getString("firstname");
    var lastName = row.getString("lastname");
    var email = row.getString("email");
    var phone = row.getString("phone");
    return new User(id, fistName, lastName, email, phone, null);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User user)) {
      return false;
    }
    if (!Objects.equals(firstName, user.firstName)) {
      return false;
    }
    if (!Objects.equals(lastName, user.lastName)) {
      return false;
    }
    if (!Objects.equals(email, user.email)) {
      return false;
    }
    if (!Objects.equals(phone, user.phone)) {
      return false;
    }
    return Objects.equals(transfers, user.transfers);
  }

  @Override
  public int hashCode() {
    int result = firstName != null ? firstName.hashCode() : 0;
    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (phone != null ? phone.hashCode() : 0);
    result = 31 * result + (transfers != null ? transfers.hashCode() : 0);
    return result;
  }
}
