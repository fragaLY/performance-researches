package by.vk.datagen.data.city;

import by.vk.datagen.data.country.Country;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CityGenerator {

  private final CityRepository cityRepository;

  public void generate() {
    log.info("[CITY GENERATION] Started.");
    var mogilev = new City(null, "Mogilev", "22", new Country(1L));
    var gomel = new City(null, "Gomel", "23", new Country(1L));
    var vitebsk = new City(null, "Vitebsk", "21", new Country(1L));
    var brest = new City(null, "Brest", "16", new Country(1L));
    var grodno = new City(null, "Grodno", "15", new Country(1L));
    var minsk = new City(null, "Minsk", "17", new Country(1L));
    var cities = List.of(mogilev, gomel, vitebsk, brest, grodno, minsk);
    var savedCities = cityRepository.saveAll(cities);
    log.info("[CITY GENERATION] Ended.");
  }
}
