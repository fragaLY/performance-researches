package by.vk.transfer;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
import java.util.Date;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@R2dbcRepository(dialect = Dialect.POSTGRES)
public
interface TransferRepository extends ReactiveStreamsCrudRepository<Transfer, Long> {

  @NonNull
  @Join("origin")
  @Join("destination")
  Flux<Transfer> findByOriginIdAndDestinationIdAndDate(Long originId, Long destinationId,
      Date date);

  @Override
  Mono<Transfer> findById(Long id);
}
