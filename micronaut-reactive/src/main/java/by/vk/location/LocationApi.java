package by.vk.location;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;

@Controller("/countries")
public record LocationApi(Service service) {

  @Get("/{countryId}/cities/{cityId}/locations")
  public Flux<Location> locations(@SuppressWarnings("unused") Long countryId, Long cityId) {
    return service.locations(cityId);
  }
}

@Singleton
record Service(Repository repository) {

  Flux<Location> locations(Long cityId) {
    return Flux.from(repository.findByCityId(cityId));
  }

}

@R2dbcRepository(dialect = Dialect.POSTGRES)
interface Repository extends ReactiveStreamsCrudRepository<Location, Long> {

  @NonNull
  @Join("city")
  Flux<Location> findByCityId(Long cityId);
}