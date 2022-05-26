package by.vk.springbootwebnative.location.api;

import by.vk.springbootwebnative.location.LocationService;
import by.vk.springbootwebnative.location.api.response.CityResponse;
import by.vk.springbootwebnative.location.api.response.CountryResponse;
import by.vk.springbootwebnative.location.api.response.LocationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("api/v1/countries")
public record LocationAPI(LocationService service) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CountryResponse> countries() {
        return service.countries();
    }

    @GetMapping("/{countryId}/cities")
    @ResponseStatus(HttpStatus.OK)
    public List<CityResponse> cities(
            @NotNull(message = "The id of country should not be null") @PathVariable Long countryId) {
        return service.cities(countryId);
    }

    @GetMapping("/{countryId}/cities/{cityId}/locations")
    @ResponseStatus(HttpStatus.OK)
    List<LocationResponse> locations(
            @NotNull(message = "The id of country should not be null") @PathVariable Long countryId,
            @NotNull(message = "The id of city should not be null") @PathVariable Long cityId) {
        return service.locations(countryId, cityId);
    }

}
