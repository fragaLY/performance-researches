package by.vk.quarkusweb.country;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;

@Path("/api/v1/countries")
@AllArgsConstructor
public class CountryResource {

  @Inject
  PgPool client;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Multi<Country> all() {
    return Country.all(client);
  }
}