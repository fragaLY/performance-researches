package by.vk.datagen;

import by.vk.datagen.data.city.CityGenerator;
import by.vk.datagen.data.country.CountryGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DatagenApplication implements CommandLineRunner {

    private final CountryGenerator countryGenerator;
    private final CityGenerator cityGenerator;

    public DatagenApplication(CountryGenerator countryGenerator, CityGenerator cityGenerator) {
        this.countryGenerator = countryGenerator;
        this.cityGenerator = cityGenerator;
    }

    public static void main(String[] args) {
        SpringApplication.run(DatagenApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        var country = countryGenerator.generate();




    }
}
