package by.vk.quarkusweb.user;

import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
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
public class UserResource {

  PgPool client;

  @GET
  @Path("/{userId:[0-9]+}")
  @Produces(MediaType.APPLICATION_JSON)
  @RunOnVirtualThread
  public Uni<Response> one(Long userId) {
    return User.one(client, userId).onItem()
               .transform(it -> it != null ? Response.ok(it) : Response.status(Status.NOT_FOUND))
               .onItem().transform(ResponseBuilder::build);
  }

  @PUT
  @Path("/{userId:[0-9]+}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @RunOnVirtualThread
  public Uni<Response> update(Long userId, UserEditionPayload payload) {
    return User.update(client, userId, payload).onItem()
               .transform(
                   it -> it != null ? Response.noContent() : Response.status(Status.NOT_FOUND)
               )
               .onItem().transform(ResponseBuilder::build);
  }
}