package by.vk.springbootreactive.location.repository.location;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface LocationRepository extends R2dbcRepository<Location, Long> {

  Flux<Location> findByCityId(Long cityId);
}
