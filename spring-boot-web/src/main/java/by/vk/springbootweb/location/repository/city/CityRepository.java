package by.vk.springbootweb.location.repository.city;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Long> {

  List<City> findByCountryId(Long countryId);
}
