package by.vk.quarkusweb.transfer;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import java.time.LocalDate;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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