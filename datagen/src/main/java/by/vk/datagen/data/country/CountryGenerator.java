package by.vk.datagen.data.country;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CountryGenerator  {

    private final CountryRepository repository;

    public Country generate() {
        return repository.save(new Country(null, "Republic of Belarus", "BY"));
    }
}
