package by.vk.country;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;

@Controller("/countries")
public record CountryApi(Service service) {

  @Get
  public Flux<Country> countries() {
    return service.countries();
  }

}

@Singleton
record Service(Repository repository) {

  Flux<Country> countries() {
    return Flux.from(repository.findAll());
  }

}

@R2dbcRepository(dialect = Dialect.POSTGRES)
interface Repository extends ReactiveStreamsCrudRepository<Country, Long> {

  @NonNull
  @Override
  Flux<Country> findAll();
}
