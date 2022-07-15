package by.vk.springbootwebnative.transfer.api.response;

import by.vk.springbootwebnative.location.api.response.LocationResponse;
import by.vk.springbootwebnative.transfer.repository.Transfer;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record TransferResponse(Long transferId,
                               LocationResponse origin,
                               LocationResponse destination,
                               Short capacity,
                               List<LocalDateTime> start,
                               BigDecimal price,
                               String description) {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd HH:mm:ss");
  private static final String SPLITERATOR = ",";
  private static final int BEGIN_INDEX = 1;

  public static TransferResponse from(Transfer entity) {
    var duration = entity.getDuration();
    var range = duration.substring(BEGIN_INDEX, duration.length() - 2).split(SPLITERATOR, -1);
    var startValue = range[0].substring(1, range[0].length() - 1);
    var endValue = range[1].substring(1, range[0].length() - 1);
    var start = LocalDateTime.parse(startValue, FORMATTER);
    var end = LocalDateTime.parse(endValue, FORMATTER);
    return new TransferResponse(entity.getId(), LocationResponse.from(entity.getOrigin()),
        LocationResponse.from(entity.getDestination()), entity.getCapacity(), List.of(start, end),
        entity.getPrice(), entity.getDescription());
  }
}
