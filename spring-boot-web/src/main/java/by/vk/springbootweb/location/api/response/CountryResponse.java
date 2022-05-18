package by.vk.springbootweb.location.api.response;

import by.vk.springbootweb.location.repository.country.Country;

public record CountryResponse(Long countryId, String name, String code) {

    public static CountryResponse from(Country entity) {
        return new CountryResponse(entity.getId(), entity.getName(), entity.getCode());
    }
}
