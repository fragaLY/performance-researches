package by.vk.datagen.data.user;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
@Slf4j
public class UserGenerator {

    private final Faker faker;
    private final UserRepository repository;

    public void generate() {
        log.info("[USERS GENERATION] Started.");
        repository.saveAll(
                IntStream.rangeClosed(1, 200_000).parallel()
                        .mapToObj(it -> new User(null, faker.name().firstName(), faker.name().lastName(), faker.funnyName().name() + it + "@gmail.com", faker.phoneNumber().phoneNumber()))
                        .collect(Collectors.toList())
        );
        log.info("[USERS GENERATION] Ended.");
    }
}
