package by.vk.datagen.data.userstransfers;

import by.vk.datagen.data.transfer.Transfer;
import by.vk.datagen.data.user.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
@Slf4j
public class UsersTransfersGenerator {

    private final Faker faker;
    private final UsersTransfersRepository repository;

    public void generate() {
        log.info("[USERS_TRANSFERS GENERATION] Started.");
        var usersTransfers =
                IntStream.rangeClosed(1, 500_000).parallel()
                        .mapToObj(it -> {
                            var user = new User(ThreadLocalRandom.current().nextLong(1, 200_001));
                            var transfer = new Transfer(ThreadLocalRandom.current().nextLong(1, 12_001));
                            var state = State.values()[ThreadLocalRandom.current().nextInt(0, 2)];
                            return new UsersTransfers(new UsersTransfersId(user.getId(), transfer.getId()), user, transfer, state, faker.backToTheFuture().quote());
                        })
                        .collect(Collectors.toList());
        repository.saveAll(usersTransfers);
        log.info("[USERS_TRANSFERS GENERATION] Ended.");
    }
}
