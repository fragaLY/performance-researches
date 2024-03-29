package by.vk.springbootreactivenative.location.responses;


import by.vk.springbootreactivenative.location.repository.location.Location;

public record LocationResponse(Long locationId, CityResponse city, Double latitude,
                               Double longitude) {

  public static LocationResponse from(Location entity) {
    return new LocationResponse(entity.getId(), CityResponse.from(entity.getCity()),
        entity.getLocation().latitude(), entity.getLocation().longitude());
  }
}
