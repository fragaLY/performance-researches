package by.vk.datagen.data.location;

import by.vk.datagen.data.city.City;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class LocationGenerator {

    private final Faker faker;
    private final LocationRepository repository;

    public Iterable<Location> generate(City city) {
        return repository.saveAll(IntStream.rangeClosed(1, 20)
                .mapToObj(it -> new Location(null, new Point(ThreadLocalRandom.current().nextDouble(0.0, 180.0), ThreadLocalRandom.current().nextDouble(0.0, 180.0)), city))
                .collect(Collectors.toList()));
    }
}
