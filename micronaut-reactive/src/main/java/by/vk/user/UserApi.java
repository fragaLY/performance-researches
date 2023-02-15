package by.vk.user;

import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

@R2dbcRepository(dialect = Dialect.POSTGRES)
interface Repository extends ReactiveStreamsCrudRepository<User, Long> {

  @Override
  Mono<User> findById(Long id);

  @Override
  Mono<Void> update(User entity);
}

@Controller("/users")
public record UserApi(Service service) {

  @Get("/{id}")
  public Mono<User> users(Long id) {
    return service.one(id);
  }

  @Put("/{userId}")
  public Mono<Void> update(Long userId, @Body UserEditionPayload payload) {
    return service.update(userId, payload);
  }
}

@Singleton
record Service(Repository repository) {

  Mono<User> one(Long id) {
    return Mono.from(repository.findById(id));
  }

  Mono<Void> update(Long id, UserEditionPayload payload) {
    return repository.findById(id)
                     .flatMap(it -> {
                       it.setLastName(payload.lastName());
                       it.setFirstName(payload.firstName());
                       return repository.update(it);
                     })
                     .then();
  }

}