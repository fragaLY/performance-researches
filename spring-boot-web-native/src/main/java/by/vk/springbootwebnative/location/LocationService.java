package by.vk.springbootwebnative.location;

import by.vk.springbootweb.location.api.response.CityResponse;
import by.vk.springbootweb.location.api.response.CountryResponse;
import by.vk.springbootweb.location.api.response.LocationResponse;
import by.vk.springbootweb.location.repository.city.CityRepository;
import by.vk.springbootweb.location.repository.country.CountryRepository;
import by.vk.springbootweb.location.repository.location.LocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public record LocationService(CountryRepository countryRepository,
                              CityRepository cityRepository,
                              LocationRepository locationRepository) {

    public List<CountryResponse> countries() {
        log.info("[LOCATION SERVICE] Retrieving countries");
        return StreamSupport.stream(countryRepository.findAll().spliterator(), true)
                .map(CountryResponse::from)
                .collect(Collectors.toList());
    }

    public List<CityResponse> cities(Long countryId) {
        log.info("[LOCATION SERVICE] Retrieving cities for country with id [{}]", countryId);
        return cityRepository.findByCountryId(countryId)
                .parallelStream()
                .map(CityResponse::from)
                .collect(Collectors.toList());
    }

    public List<LocationResponse> locations(Long countryId, Long cityId) {
        log.info("[LOCATION SERVICE] Retrieving locations for country with id [{}] and  city with id [{}]", countryId, cityId);
        return locationRepository.findByCityId(cityId)
                .parallelStream()
                .map(LocationResponse::from)
                .collect(Collectors.toList());
    }
}
