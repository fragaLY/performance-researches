package by.vk.springbootreactive.location.responses;


import by.vk.springbootreactive.location.repository.country.Country;

public record CountryResponse(Long countryId, String name, String code) {

  public static CountryResponse from(Country entity) {
    return new CountryResponse(entity.getId(), entity.getName(), entity.getCode());
  }
}
