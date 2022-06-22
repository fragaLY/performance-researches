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

@SpringBootApplication
@AllArgsConstructor
public class DatagenApplication implements CommandLineRunner {

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
    userGenerator.generate();
    countryGenerator.generate();
    cityGenerator.generate();
    locationGenerator.generate();
    transferGenerator.generate();
    usersTransfersGenerator.generate();
  }
}
