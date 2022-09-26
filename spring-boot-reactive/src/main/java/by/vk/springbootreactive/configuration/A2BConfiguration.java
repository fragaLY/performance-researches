package by.vk.springbootreactive.configuration;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import by.vk.springbootreactive.location.handler.LocationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class A2BConfiguration {

  @Bean
  public RouterFunction<ServerResponse> route(LocationHandler locationHandler) {

    return RouterFunctions
        .route(GET("/countries").and(accept(APPLICATION_JSON)), locationHandler::countries)
        .andRoute(
            GET("/countries/{countryId:[0-9]+}/cities/")
                .and(accept(APPLICATION_JSON)), locationHandler::cities)
        .andRoute(
            GET("/countries/{countryId:[0-9]+}/cities/{cityId:[0-9]+}/locations")
                .and(accept(APPLICATION_JSON)), locationHandler::locations);
  }

  //todo vk: add exception handling
  //todo vk: add retry and etc.
  //todo vk: add user api support
  //todo vk: add transfers support

}
