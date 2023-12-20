package by.vk.quarkusweb.user.transfer;

import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;
import lombok.RequiredArgsConstructor;

@Path("/api/v1/users")
@RequiredArgsConstructor
public class UserTransferResource {

  PgPool client;

  @GET
  @Path("/{userId:[0-9]+}/transfers")
  @Produces(MediaType.APPLICATION_JSON)
  @RunOnVirtualThread
  public Multi<UsersTransfers> transfers(Long userId) {
    return UsersTransfers.transfers(client, userId);
  }

  @GET
  @Path("/{userId:[0-9]+}/transfers/{transferId:[0-9]+}")
  @Produces(MediaType.APPLICATION_JSON)
  @RunOnVirtualThread
  public Uni<Response> transfer(Long userId, Long transferId) {
    return UsersTransfers.transfer(client, userId, transferId)
                         .onItem()
                         .transform(
                             it -> it != null ? Response.ok(it) : Response.status(Status.NOT_FOUND)
                         )
                         .onItem()
                         .transform(ResponseBuilder::build);
  }

  @PUT
  @Path("/{userId:[0-9]+}/transfers/{transferId:[0-9]+}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @RunOnVirtualThread
  public Uni<Response> update(Long userId, Long transferId, UserTransferEditionPayload payload) {
    return UsersTransfers.update(client, userId, transferId, payload).onItem().transform(
                             it -> it != null ? Response.noContent() : Response.status(Status.NOT_FOUND)).onItem()
                         .transform(ResponseBuilder::build);
  }

  @POST
  @Path("/{userId:[0-9]+}/transfers/{transferId:[0-9]+}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @RunOnVirtualThread
  public Uni<Response> create(Long userId, Long transferId, UserTransferCreationPayload payload) {
    return UsersTransfers.create(client, userId, transferId, payload)
                         .onItem()
                         .transform(
                             it -> it != null ? Response.ok() : Response.status(Status.NOT_FOUND))
                         .onItem()
                         .transform(ResponseBuilder::build);
  }
}