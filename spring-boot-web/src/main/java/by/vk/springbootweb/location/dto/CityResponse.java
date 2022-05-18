package by.vk.springbootweb.location.dto;

import by.vk.springbootweb.location.repository.city.City;

public record CityResponse(Long cityId, CountryResponse country, String name, String code) {

    public static CityResponse from(City entity) {
        return new CityResponse(entity.getId(), CountryResponse.from(entity.getCountry()), entity.getName(), entity.getCode());
    }
}
