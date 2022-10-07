package by.vk.quarkusweb.country;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
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
public class Country {

  private static final String GET_ALL_COUNTRIES_QUERY = "select * from a2b.countries;";

  private Long id;
  private String name;
  private String code;

  static Country from(Row row) {
    return new Country(row.getLong("id"), row.getString("name"), row.getString("code"));
  }

  static Multi<Country> all(PgPool client) {
    return client.query(GET_ALL_COUNTRIES_QUERY).execute()
                 .onItem().transformToMulti(rowSet -> Multi.createFrom().iterable(rowSet))
                 .onItem().transform(Country::from);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Country country)) {
      return false;
    }

    if (!Objects.equals(name, country.name)) {
      return false;
    }
    return Objects.equals(code, country.code);
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (code != null ? code.hashCode() : 0);
    return result;
  }
}
