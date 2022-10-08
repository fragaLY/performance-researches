package by.vk.quarkusweb.user;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
public class UserResource {

  @Inject
  PgPool client;

  @GET
  @Path("/{userId:[0-9]+}")
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> one(Long userId) {
    return User.one(client, userId).onItem()
               .transform(it -> it != null ? Response.ok(it) : Response.status(Status.NOT_FOUND))
               .onItem().transform(ResponseBuilder::build);
  }

  @PUT
  @Path("/{userId:[0-9]+}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> update(Long userId, UserEditionPayload payload) {
    return User.update(client, userId, payload).onItem()
               .transform(
                   it -> it != null ? Response.noContent() : Response.status(Status.NOT_FOUND)
               )
               .onItem().transform(ResponseBuilder::build);
  }
}