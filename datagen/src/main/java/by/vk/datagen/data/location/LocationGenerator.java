package by.vk.datagen.data.location;

import by.vk.datagen.data.city.City;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class LocationGenerator {

  private static final int LOCATIONS_PER_CITY_AMOUNT = 120;
  private final LocationRepository repository;

  public void generate() {
    log.info("[LOCATIONS GENERATION] Started.");
    repository.saveAll(IntStream.rangeClosed(1, LOCATIONS_PER_CITY_AMOUNT).parallel().mapToObj(
            it -> new Location(null, new Point(ThreadLocalRandom.current().nextDouble(-90.0, 90.0),
                ThreadLocalRandom.current().nextDouble(-180.0, 180.0)),
                new City(ThreadLocalRandom.current().nextLong(1, 7))))
        .toList());
    log.info("[LOCATIONS GENERATION] Ended.");
  }
}
