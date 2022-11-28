package by.vk.springbootweb.transfer.api.response;

import by.vk.springbootweb.location.api.response.LocationResponse;
import by.vk.springbootweb.transfer.repository.Transfer;
import java.math.BigDecimal;

public record TransferResponse(Long transferId,
                               LocationResponse origin,
                               LocationResponse destination,
                               Short capacity,
                               String duration,
                               BigDecimal price,
                               String description) {

  public static TransferResponse from(Transfer entity) {
    return new TransferResponse(entity.getId(), LocationResponse.from(entity.getOrigin()),
        LocationResponse.from(entity.getDestination()), entity.getCapacity(), entity.getDuration(),
        entity.getPrice(), entity.getDescription());
  }
}
