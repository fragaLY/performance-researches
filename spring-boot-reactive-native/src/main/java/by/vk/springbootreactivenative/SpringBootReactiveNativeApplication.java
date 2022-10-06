package by.vk.springbootreactivenative;

import by.vk.springbootreactivenative.location.responses.CityResponse;
import by.vk.springbootreactivenative.location.responses.CountryResponse;
import by.vk.springbootreactivenative.location.responses.LocationResponse;
import by.vk.springbootreactivenative.transfer.responses.TransferResponse;
import by.vk.springbootreactivenative.user.payload.UserEditionPayload;
import by.vk.springbootreactivenative.user.payload.UserTransferCreationPayload;
import by.vk.springbootreactivenative.user.payload.UserTransferEditionPayload;
import by.vk.springbootreactivenative.user.responses.UserResponse;
import by.vk.springbootreactivenative.user.responses.UserTransferResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.nativex.hint.TypeAccess;
import org.springframework.nativex.hint.TypeHint;

@TypeHint(types = {CountryResponse.class, CityResponse.class, LocationResponse.class,
    TransferResponse.class, UserResponse.class, UserTransferResponse.class,
    UserEditionPayload.class, UserTransferCreationPayload.class,
    UserTransferEditionPayload.class}, access = {TypeAccess.DECLARED_CONSTRUCTORS,
    TypeAccess.DECLARED_METHODS, TypeAccess.DECLARED_FIELDS})
@SpringBootApplication
public class SpringBootReactiveNativeApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootReactiveNativeApplication.class, args);
  }

}
