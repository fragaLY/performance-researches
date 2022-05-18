package by.vk.springbootweb.location.dto;

import by.vk.springbootweb.location.repository.location.Location;

public record LocationResponse(Long locationId, CityResponse city, Double latitude, Double longitude) {
    public static LocationResponse from(Location entity) {
        return new LocationResponse(entity.getId(), CityResponse.from(entity.getCity()), entity.getLocation().latitude(), entity.getLocation().longitude());
    }
}
