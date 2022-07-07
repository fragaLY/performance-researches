package by.vk.springbootreactive.configuration;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import by.vk.springbootreactive.location.handler.LocationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class A2BConfiguration {

  @Bean
  public RouterFunction<ServerResponse> route(LocationHandler locationHandler) {

    return RouterFunctions
        .route(GET("/hello").and(accept(MediaType.APPLICATION_JSON)), locationHandler::hello);
  }

}
