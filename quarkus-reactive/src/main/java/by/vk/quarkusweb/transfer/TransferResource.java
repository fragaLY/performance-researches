package by.vk.quarkusweb.transfer;

import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestQuery;

@Path("/api/v1/transfers")
@RequiredArgsConstructor
public class TransferResource {

  PgPool client;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @RunOnVirtualThread
  public Multi<Transfer> all(@RestQuery Long originId, @RestQuery Long destinationId,
      @RestQuery LocalDate date) {
    return Transfer.all(client, originId, destinationId, date);
  }
}