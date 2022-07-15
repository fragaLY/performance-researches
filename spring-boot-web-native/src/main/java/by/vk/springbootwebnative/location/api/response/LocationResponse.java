package by.vk.springbootwebnative.location.api.response;

import by.vk.springbootwebnative.exception.BadRequestException;
import by.vk.springbootwebnative.location.repository.location.Location;
import by.vk.springbootwebnative.location.repository.location.Point;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public record LocationResponse(Long locationId, CityResponse city, Double latitude,
                               Double longitude) {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static LocationResponse from(Location entity) {
    Point point;
    try {
      point = OBJECT_MAPPER.readValue(entity.getLocation(), Point.class);
    } catch (JsonProcessingException e) {
      throw new BadRequestException("Unsupported location point deserialization operation.");
    }
    return new LocationResponse(entity.getId(), CityResponse.from(entity.getCity()),
        point.latitude(), point.longitude());
  }
}
