package by.vk.quarkusweb.location;

import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;

@Path("/api/v1/countries/{country:[0-9]+}/cities/{city:[0-9]+}/locations")
@RequiredArgsConstructor
public class LocationResource {

  PgPool client;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @RunOnVirtualThread
  public Multi<Location> all(Long country, Long city) {
    return Location.all(client, city);
  }
}