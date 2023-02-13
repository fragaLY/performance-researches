package by.vk.transfer;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.convert.format.Format;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import jakarta.inject.Singleton;
import java.util.Date;
import reactor.core.publisher.Flux;

@R2dbcRepository(dialect = Dialect.POSTGRES)
interface Repository extends ReactiveStreamsCrudRepository<Transfer, Long> {

  @NonNull
  @Join("origin")
  @Join("destination")
  Flux<Transfer> findByOriginIdAndDestinationIdAndDate(Long originId, Long destinationId,
      Date date);
}

@Controller("/transfers")
public record TransferApi(Service service) {

  @Get
  public Flux<Transfer> transfers(@QueryValue Long originId,
      @QueryValue Long destinationId,
      @Format("yyyy-MM-dd") @QueryValue Date date) {
    return service.transfers(originId, destinationId, date);
  }
}

@Singleton
record Service(Repository repository) {

  Flux<Transfer> transfers(Long origin, Long destination, Date date) {
    return Flux.from(repository.findByOriginIdAndDestinationIdAndDate(origin, destination, date));
  }

}