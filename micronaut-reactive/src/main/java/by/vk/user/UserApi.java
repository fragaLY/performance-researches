package by.vk.user;

import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

@R2dbcRepository(dialect = Dialect.POSTGRES)
interface Repository extends ReactiveStreamsCrudRepository<User, Long> {

}

@Controller("/users")
public record UserApi(Service service) {

  @Get("/{id}")
  public Mono<User> users(Long id) {
    return service.one(id);
  }
}

@Singleton
record Service(Repository repository) {

  Mono<User> one(Long id) {
    return Mono.from(repository.findById(id));
  }

}