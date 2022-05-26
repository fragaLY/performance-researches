package by.vk.springbootwebnative.location.api.response;

import by.vk.springbootwebnative.location.repository.country.Country;

public record CountryResponse(Long countryId, String name, String code) {

    public static CountryResponse from(Country entity) {
        return new CountryResponse(entity.getId(), entity.getName(), entity.getCode());
    }
}
