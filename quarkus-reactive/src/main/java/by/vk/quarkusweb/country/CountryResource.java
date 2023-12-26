package by.vk.quarkusweb.country;

import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;

@Path("/api/v1/countries")
@RequiredArgsConstructor
public class CountryResource {

  PgPool client;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Multi<Country> all() {
    return Country.all(client);
  }
}