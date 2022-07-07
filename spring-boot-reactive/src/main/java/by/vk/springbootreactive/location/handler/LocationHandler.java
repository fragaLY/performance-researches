package by.vk.springbootreactive.location.handler;

import by.vk.springbootreactive.location.repository.city.CityRepository;
import by.vk.springbootreactive.location.repository.country.CountryRepository;
import by.vk.springbootreactive.location.repository.location.LocationRepository;
import by.vk.springbootreactive.responses.CountryResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

@Component
public record LocationHandler(CountryRepository countryRepository,
                              CityRepository cityRepository,
                              LocationRepository locationRepository) {


  public Flux<ServerResponse> countries(ServerRequest request) {
    return ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .build(countryRepository.findAll().mapNotNull(CountryResponse::from), CountryResponse.class);
  }

  public Flux<ServerResponse> cities(ServerRequest request) {
    return ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue());
  }
  public Flux<ServerResponse> locations(ServerRequest request) {
    return ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue());
  }


}
