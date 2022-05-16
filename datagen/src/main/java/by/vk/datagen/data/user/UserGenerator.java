package by.vk.datagen.data.user;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class UserGenerator {

    private final Faker faker;
    private final UserRepository repository;

    void generate() {
        IntStream.rangeClosed(1, 200_000).mapToObj(it -> new User(null, faker.name().firstName(), faker.name().lastName(), faker.funnyName().name() + it + "@gmail.com", faker.phoneNumber().phoneNumber())).forEach(repository::save);
    }
}
