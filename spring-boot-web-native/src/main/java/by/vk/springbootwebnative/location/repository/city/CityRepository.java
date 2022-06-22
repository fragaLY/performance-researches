package by.vk.springbootwebnative.location.repository.city;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Long> {

  List<City> findByCountryId(Long countryId);
}
