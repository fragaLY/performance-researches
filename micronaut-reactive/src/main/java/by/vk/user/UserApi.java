package by.vk.user;

import io.micronaut.data.r2dbc.operations.R2dbcOperations;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.Status;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

@Controller("/users")
public record UserApi(Service service) {

  @Get("/{id}")
  public Mono<User> users(Long id) {
    return service.one(id);
  }

  @Status(HttpStatus.NO_CONTENT)
  @Put("/{userId}")
  public Mono<Void> update(Long userId, @Body UserEditionPayload payload) {
    return service.update(userId, payload);
  }
}

@Singleton
class Service {

  private final UserRepository repository;
  private final R2dbcOperations operations;

  public Service(UserRepository repository, R2dbcOperations operations) {
    this.repository = repository;
    this.operations = operations;
  }

  Mono<User> one(Long id) {
    return repository.findById(id);
  }

  Mono<Void> update(Long id, UserEditionPayload payload) {
    return Mono.from(operations.withTransaction(status -> repository.findById(id)
                                                                    .flatMap(it -> {
                                                                      it.setLastName(
                                                                          payload.lastName());
                                                                      it.setFirstName(
                                                                          payload.firstName());
                                                                      return repository.update(it);
                                                                    }).then()));
  }

}