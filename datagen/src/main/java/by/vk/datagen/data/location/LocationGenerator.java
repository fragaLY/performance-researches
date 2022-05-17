package by.vk.datagen.data.location;

import by.vk.datagen.data.city.City;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
@Slf4j
public class LocationGenerator {
    private final LocationRepository repository;

    public Iterable<Location> generate(City city, int amount) {
        log.info("[LOCATIONS GENERATION] Started.");
        var savedLocations = repository.saveAll(IntStream.rangeClosed(1, amount).parallel().mapToObj(it -> new Location(null, new Point(ThreadLocalRandom.current().nextDouble(0.0, 180.0), ThreadLocalRandom.current().nextDouble(0.0, 180.0)), city))
                .collect(Collectors.toList()));
        log.info("[LOCATIONS GENERATION] Ended.");
        return savedLocations;
    }
}
