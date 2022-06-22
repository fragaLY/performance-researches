package by.vk.datagen.configuration;

import net.datafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfiguration {

  @Bean
  public Faker faker() {
    return new Faker();
  }
}
