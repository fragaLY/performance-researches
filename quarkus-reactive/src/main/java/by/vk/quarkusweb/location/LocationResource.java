package by.vk.quarkusweb.location;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;

@Path("/api/v1/countries/{country:[0-9]+}/cities/{city:[0-9]+}/locations")
@AllArgsConstructor
public class LocationResource {

  @Inject
  PgPool client;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Multi<Location> all(Long country, Long city) {
    return Location.all(client, city);
  }
}