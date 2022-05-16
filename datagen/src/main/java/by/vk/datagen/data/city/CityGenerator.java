package by.vk.datagen.data.city;

import by.vk.datagen.data.country.Country;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CityGenerator {

    private final CityRepository repository;

    public Iterable<City> generate(String name, String code, Country country) {
        var mogilev = new City(null, "Mogilev", "22", country);
        var gomel = new City(null, "Gomel", "23", country);
        var vitebsk = new City(null, "Vitebsk", "21", country);
        var brest = new City(null, "Brest", "16", country);
        var grodno = new City(null, "Grodno", "15", country);
        var minsk = new City(null, "Minsk", "17", country);
        var cities = List.of(mogilev, gomel, vitebsk, brest, grodno, minsk);
        return repository.saveAll(cities);
    }
}
