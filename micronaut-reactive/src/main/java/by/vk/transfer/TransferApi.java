package by.vk.transfer;

import io.micronaut.core.convert.format.Format;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import jakarta.inject.Singleton;
import java.util.Date;
import reactor.core.publisher.Flux;

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
record Service(TransferRepository repository) {

  Flux<Transfer> transfers(Long origin, Long destination, Date date) {
    return repository.findByOriginIdAndDestinationIdAndDate(origin, destination, date);
  }

}