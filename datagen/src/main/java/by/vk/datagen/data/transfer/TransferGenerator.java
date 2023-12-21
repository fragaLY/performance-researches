package by.vk.datagen.data.transfer;

import by.vk.datagen.data.location.Location;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class TransferGenerator {

  private final Faker faker;
  private final TransferRepository repository;

  public void generate() {
    log.info("[TRANSFERS GENERATION] Started.");
    var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LongStream.rangeClosed(1, 12_000).parallel().mapToObj(it -> {
      var now = LocalDate.now();
      var from = now.minusMonths(1);
      var to = now.plusMonths(1);
      var capacity = Short.valueOf((short) ThreadLocalRandom.current().nextInt(1, Short.MAX_VALUE));
      var price = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1.0, 100.0));
      var description = faker.lebowski().quote();
      var startDay = ThreadLocalRandom.current().nextLong(from.toEpochDay(), to.toEpochDay());
      var startTime = LocalTime.MIDNIGHT.plusHours(ThreadLocalRandom.current().nextLong(1, 6));
      var startDateTime = LocalDateTime.of(LocalDate.ofEpochDay(startDay), startTime);
      var endDay = ThreadLocalRandom.current().nextLong(startDay, to.toEpochDay());
      var endTime = LocalTime.MIDNIGHT.plusHours(ThreadLocalRandom.current().nextLong(6, 12));
      var endDateTime = LocalDateTime.of(LocalDate.ofEpochDay(endDay), endTime);
      var duration = formatter.format(startDateTime) + "," + formatter.format(endDateTime);
      var origin = new Location(ThreadLocalRandom.current().nextLong(1, 121));
      var destination = new Location(121 - origin.getId());
      return new Transfer(it, origin, destination, capacity, new Date(startDay), duration, price,
          description);
    }).forEach(
        it -> repository.create(it.getId(), it.getOrigin().getId(), it.getDestination().getId(),
            it.getCapacity(), it.getDate(),
            LocalDateTime.parse(it.getDuration().split(",")[0], formatter),
            LocalDateTime.parse(it.getDuration().split(",")[1], formatter), it.getPrice(),
            it.getDescription()));
    log.info("[TRANSFERS GENERATION] Ended.");
  }
}
