package by.vk.quarkusweb.user.transfer;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import lombok.AllArgsConstructor;

@Path("/api/v1/users")
@AllArgsConstructor
public class UserTransferResource {

  @Inject
  PgPool client;

  @GET
  @Path("/{userId:[0-9]+}/transfers")
  @Produces(MediaType.APPLICATION_JSON)
  public Multi<UsersTransfers> transfers(Long userId) {
    return UsersTransfers.transfers(client, userId);
  }

  @GET
  @Path("/{userId:[0-9]+}/transfers/{transferId:[0-9]+}")
  @Produces(MediaType.APPLICATION_JSON)
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
  public Uni<Response> update(Long userId, Long transferId, UserTransferEditionPayload payload) {
    return UsersTransfers.update(client, userId, transferId, payload).onItem().transform(
                             it -> it != null ? Response.noContent() : Response.status(Status.NOT_FOUND)).onItem()
                         .transform(ResponseBuilder::build);
  }

  @POST
  @Path("/{userId:[0-9]+}/transfers/{transferId:[0-9]+}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> create(Long userId, Long transferId, UserTransferCreationPayload payload) {
    return UsersTransfers.create(client, userId, transferId, payload)
                         .onItem()
                         .transform(
                             it -> it != null ? Response.ok() : Response.status(Status.NOT_FOUND))
                         .onItem()
                         .transform(ResponseBuilder::build);
  }
}