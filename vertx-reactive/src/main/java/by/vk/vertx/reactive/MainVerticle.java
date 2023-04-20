package by.vk.vertx.reactive;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import static by.vk.vertx.reactive.configuration.server.Server.CONTENT_TYPE;

public class MainVerticle extends AbstractVerticle {

    private HttpServer server;

    @Override
    public void start(Promise<Void> startPromise) {
        var router = Router.router(vertx);
        router.get("/countries")
              .produces(CONTENT_TYPE.value())
              .handler(ctx -> {
                  System.out.println("Countries were found");
                  ctx.response()
                     .end();
              });

        router.get("/countries/:countryId/cities")
              .produces(CONTENT_TYPE.value())
              .handler(ctx -> {
                  var countryId = ctx.pathParam("countryId");
                  System.out.println("Cities were found");
                  ctx.response()
                     .end();
              });

        router.get("/countries/:countryId/cities/:cityId/locations")
              .produces(CONTENT_TYPE.value())
              .handler(ctx -> {
                  System.out.println("Locations were found");
                  var countryId = ctx.pathParam("countryId");
                  var cityId = ctx.pathParam("cityId");
                  ctx.response()
                     .end();
              });

//        var api = Router.router(vertx);
//        api.route("/api/v1/*")
//           .method(HttpMethod.GET)
//           .method(HttpMethod.POST)
//           .method(HttpMethod.PUT)
//           .method(HttpMethod.DELETE)
//           .failureHandler(MainVerticle::errorHandling)
//           .subRouter(router);

        var server = vertx.createHttpServer();
        server.requestHandler(router)
              .listen(8080, http -> {
                  if (http.succeeded()) {
                      startPromise.complete();
                  } else {
                      startPromise.fail(http.cause());
                  }
              });
    }

    @Override
    public void stop(Promise<Void> promise) {
        server.close(res -> server.close(promise));
    }

    private static void errorHandling(RoutingContext ctx) {
        System.out.println(ctx.response().getStatusMessage());

        ctx.response()
           .setStatusCode(404)
           .setStatusMessage("The API doesn't found a handler.")
           .end();
    }

}
