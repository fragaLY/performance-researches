package by.vk.user;

import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
import reactor.core.publisher.Mono;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public interface UserRepository extends ReactiveStreamsCrudRepository<User, Long> {

  @Override
  Mono<User> findById(Long id);

  @Override
  Mono<Void> update(User entity);
}