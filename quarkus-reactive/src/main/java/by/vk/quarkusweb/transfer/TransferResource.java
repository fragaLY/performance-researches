package by.vk.quarkusweb.transfer;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import java.time.LocalDate;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import org.jboss.resteasy.reactive.RestQuery;

@Path("/api/v1/transfers")
@AllArgsConstructor
public class TransferResource {

  @Inject
  PgPool client;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Multi<Transfer> all(@RestQuery Long originId, @RestQuery Long destinationId,
      @RestQuery LocalDate date) {
    return Transfer.all(client, originId, destinationId, date);
  }
}