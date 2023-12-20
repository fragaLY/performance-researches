package by.vk.quarkusweb.city;

import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;

@Path("/api/v1/countries/{country:[0-9]+}/cities")
@RequiredArgsConstructor
public class CityResource {

  PgPool client;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @RunOnVirtualThread
  public Multi<City> all(Long country) {
    return City.all(client, country);
  }
}