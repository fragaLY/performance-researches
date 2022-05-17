package by.vk.datagen;

import by.vk.datagen.data.city.CityGenerator;
import by.vk.datagen.data.country.CountryGenerator;
import by.vk.datagen.data.location.LocationGenerator;
import by.vk.datagen.data.transfer.TransferGenerator;
import by.vk.datagen.data.user.UserGenerator;
import by.vk.datagen.data.userstransfers.UsersTransfersGenerator;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.stream.StreamSupport;

@SpringBootApplication
@AllArgsConstructor
public class DatagenApplication implements CommandLineRunner {

    private static final int LOCATIONS_PER_CITY_AMOUNT = 20;
    private final CountryGenerator countryGenerator;
    private final CityGenerator cityGenerator;
    private final LocationGenerator locationGenerator;
    private final UserGenerator userGenerator;
    private final TransferGenerator transferGenerator;
    private final UsersTransfersGenerator usersTransfersGenerator;

    public static void main(String[] args) {
        SpringApplication.run(DatagenApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var users = StreamSupport.stream(userGenerator.generate().spliterator(), true).toList();
        var country = countryGenerator.generate();
        var cities = StreamSupport.stream(cityGenerator.generate(country).spliterator(), true).toList();
        var locations = cities.parallelStream()
                .flatMap(city -> StreamSupport.stream(locationGenerator.generate(city, LOCATIONS_PER_CITY_AMOUNT).spliterator(), false)).toList();
        var transfers = StreamSupport.stream(transferGenerator.generate(locations).spliterator(), true).toList();
        var usersTransfers = usersTransfersGenerator.generate(users, transfers);
    }
}
