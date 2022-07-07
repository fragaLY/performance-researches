package by.vk.springbootreactive.location.repository.city;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface CityRepository extends R2dbcRepository<City, Long> {

  Flux<City> findByCountryId(Long countryId);
}
