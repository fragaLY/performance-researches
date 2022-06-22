package by.vk.springbootwebnative.transfer.api.response;

import by.vk.springbootwebnative.location.api.response.LocationResponse;
import by.vk.springbootwebnative.transfer.repository.Transfer;
import com.vladmihalcea.hibernate.type.range.Range;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferResponse(Long transferId,
                               LocationResponse origin,
                               LocationResponse destination,
                               Short capacity,
                               Range<LocalDateTime> duration,
                               BigDecimal price,
                               String description) {

  public static TransferResponse from(Transfer entity) {
    return new TransferResponse(entity.getId(), LocationResponse.from(entity.getOrigin()),
        LocationResponse.from(entity.getDestination()), entity.getCapacity(), entity.getDuration(),
        entity.getPrice(), entity.getDescription());
  }
}
