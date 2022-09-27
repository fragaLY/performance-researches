package by.vk.springbootreactivenative.location.handler;

import by.vk.springbootreactivenative.location.mapper.city.CityMapper;
import by.vk.springbootreactivenative.location.mapper.location.LocationMapper;
import by.vk.springbootreactivenative.location.repository.country.CountryRepository;
import by.vk.springbootreactivenative.location.responses.CityResponse;
import by.vk.springbootreactivenative.location.responses.CountryResponse;
import by.vk.springbootreactivenative.location.responses.LocationResponse;
import org.springframework.http.MediaType;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public record LocationHandler(DatabaseClient client,
                              CountryRepository countryRepository,
                              CityMapper cityMapper,
                              LocationMapper locationMapper) {

  private static final String CITIES_BY_COUNTRY_QUERY_VALUE = "select ci.id as id, ci.name as name, ci.code as code, co.id as country_id, co.name as country_name, co.code as country_code from cities ci join countries co on ci.country_id = co.id where ci.country_id = :countryId";
  private static final String LOCATIONS_BY_CITY_QUERY_VALUE = "select l.id as id, l.location as location, l.city_id as city_id, ci.name as city_name, ci.code as city_code, co.id as country_id, co.name as country_name, co.code as country_code from locations l join cities ci on ci.id = l.city_id join countries co on co.id = ci.country_id where l.city_id = :cityId";

  public Mono<ServerResponse> countries(ServerRequest request) {
    return ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(countryRepository.findAll().mapNotNull(CountryResponse::from), CountryResponse.class)
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> cities(ServerRequest request) {
    var countryId = Long.parseLong(request.pathVariable("countryId"));
    var body = client.sql(CITIES_BY_COUNTRY_QUERY_VALUE)
                     .bind("countryId", countryId)
                     .map(cityMapper)
                     .all()
                     .onErrorComplete()
                     .mapNotNull(CityResponse::from);
    return ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(body, CityResponse.class)
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> locations(ServerRequest request) {
    var cityId = Long.parseLong(request.pathVariable("cityId"));
    var body = client.sql(LOCATIONS_BY_CITY_QUERY_VALUE)
                     .bind("cityId", cityId)
                     .map(locationMapper)
                     .all()
                     .onErrorComplete()
                     .mapNotNull(LocationResponse::from);
    return ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(body, LocationResponse.class)
        .switchIfEmpty(ServerResponse.notFound().build());
  }

}
