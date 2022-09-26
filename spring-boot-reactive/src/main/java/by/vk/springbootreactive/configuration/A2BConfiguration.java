package by.vk.springbootreactive.configuration;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

import by.vk.springbootreactive.location.handler.LocationHandler;
import by.vk.springbootreactive.transfer.handler.TransferHandler;
import by.vk.springbootreactive.user.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class A2BConfiguration {

  //@formatter:off
  @Bean
  public RouterFunction<ServerResponse> route(LocationHandler location, UserHandler user, TransferHandler transfer) {

    return RouterFunctions
        .route(GET("/countries").and(accept(APPLICATION_JSON)), location::countries)
        .andRoute(GET("/countries/{countryId:[0-9]+}/cities/").and(accept(APPLICATION_JSON)), location::cities)
        .andRoute(GET("/countries/{countryId:[0-9]+}/cities/{cityId:[0-9]+}/locations").and(accept(APPLICATION_JSON)), location::locations)
        .andRoute(GET("/transfers").and(accept(APPLICATION_JSON)), transfer::transfers)
        .andRoute(GET("/users/{userId:[0-9]+}").and(accept(APPLICATION_JSON)), user::user)
        .andRoute(GET("/users/{userId:[0-9]+}/transfers").and(accept(APPLICATION_JSON)), user::userTransfers)
        .andRoute(GET("/users/{userId:[0-9]+}/transfers/{transferId:[0-9]+}").and(accept(APPLICATION_JSON)), user::userTransfer)
        .andRoute(POST("/users/{userId:[0-9]+}/transfers/{transferId:[0-9]+}").and(contentType(APPLICATION_JSON)), user::createUserTransfer)
        .andRoute(PUT("/users/{userId:[0-9]+}/transfers/{transferId:[0-9]+}").and(contentType(APPLICATION_JSON)), user::updateUserTransfer)
        .andRoute(PUT("/users/{userId:[0-9]+}").and(contentType(APPLICATION_JSON)), user::updateUser);
  }
  //@formatter:on

  //todo vk: add exception handling
  //todo vk: add retry and etc.
}
