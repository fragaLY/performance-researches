package by.vk.datagen.data.transfer;

import by.vk.datagen.data.location.Location;
import com.github.javafaker.Faker;
import com.vladmihalcea.hibernate.type.range.Range;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
@Slf4j
public class TransferGenerator {

    private final Faker faker;
    private final TransferRepository repository;

    public Iterable<Transfer> generate(List<Location> locations) {
        log.info("[TRANSFERS GENERATION] Started.");
        var savedTransfers = repository.saveAll(IntStream.rangeClosed(1, 12_000).parallel().mapToObj(it -> {
            var now = LocalDate.now();
            var from = Date.from(now.minusMonths(1).atStartOfDay().toInstant(ZoneOffset.UTC));
            var to = Date.from(now.plusMonths(1).atStartOfDay().toInstant(ZoneOffset.UTC));
            var capacity = Short.valueOf((short) ThreadLocalRandom.current().nextInt(1, Short.MAX_VALUE));
            var transferDate =  faker.date().between(from, to);
            var price = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1.0, 100.0));
            var description = faker.lebowski().quote();
            final LocalDateTime[] range = {LocalDateTime.from(transferDate.toInstant()), LocalDateTime.from(transferDate.toInstant()).plusHours(2)};
            var duration = Range.localDateTimeRange(Arrays.deepToString(range));
            var origin = locations.get(ThreadLocalRandom.current().nextInt(0, 120));
            var destination = locations.get(ThreadLocalRandom.current().nextInt(0, 120));
            return new Transfer(null, origin, destination, capacity, transferDate, duration, price, description);
        }).collect(Collectors.toList()));
        log.info("[TRANSFERS GENERATION] Ended.");
        return savedTransfers;
    }
}
