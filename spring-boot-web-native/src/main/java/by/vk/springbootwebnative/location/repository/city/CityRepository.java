package by.vk.springbootwebnative.location.repository.city;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CityRepository extends CrudRepository<City, Long> {
    List<City> findByCountryId(Long countryId);
}
