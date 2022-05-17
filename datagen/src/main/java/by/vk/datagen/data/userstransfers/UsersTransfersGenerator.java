package by.vk.datagen.data.userstransfers;

import by.vk.datagen.data.transfer.Transfer;
import by.vk.datagen.data.user.User;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        repository.saveAll(
                IntStream.rangeClosed(1, 500_000).parallel()
                        .mapToObj(it -> new UsersTransfers(null, new User(ThreadLocalRandom.current().nextLong(1, 200_001)), new Transfer(ThreadLocalRandom.current().nextLong(1, 15_001)), State.values()[ThreadLocalRandom.current().nextInt(0, 2)], faker.backToTheFuture().quote()))
                        .collect(Collectors.toList()));
        log.info("[USERS_TRANSFERS GENERATION] Ended.");
    }
}
