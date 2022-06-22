package by.vk.springbootreactive.location.handler;

import by.vk.springbootreactive.location.response.CountryResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public record LocationHandler() {

  public Mono<ServerResponse> country(ServerRequest request) {
    return ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(new CountryResponse(1L, "Belarus", "BY")));
  }
}
