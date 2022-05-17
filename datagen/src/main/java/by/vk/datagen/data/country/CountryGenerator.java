package by.vk.datagen.data.country;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CountryGenerator {

    private final CountryRepository repository;

    public void generate() {
        log.info("[COUNTRY GENERATION] Started.");
        repository.save(new Country(null, "Republic of Belarus", "BY"));
        log.info("[COUNTRY GENERATION] Ended.");
    }
}
