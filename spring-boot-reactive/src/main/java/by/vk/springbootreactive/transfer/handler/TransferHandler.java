package by.vk.springbootreactive.transfer.handler;

import by.vk.springbootreactive.exception.BadRequestException;
import by.vk.springbootreactive.transfer.mapper.TransferMapper;
import by.vk.springbootreactive.transfer.responses.TransferResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.http.MediaType;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public record TransferHandler(TransferMapper transferMapper, DatabaseClient client) {

  private static final String TRANSFERS_BY_ORIGIN_AND_DESTINATION_AND_DATE =
      "select t.id as id, t.origin as transfer_origin_id, t.destination as transfer_destination_id, t.capacity as capacity, t.date as date, t.duration as duration, t.price::numeric as price, t.description as description,\n"
          + "       o.id as transfer_origin_id, o.location as transfer_origin_location, d.id as transfer_destination_id, d.location as transfer_destination_location,\n"
          + "       dc.id as transfer_destination_city_id, dc.name as transfer_destination_city_name, dc.code as transfer_destination_city_code,\n"
          + "       oc.id as transfer_origin_city_id, oc.name as transfer_origin_city_name, oc.code as transfer_origin_city_code,\n"
          + "       oco.id as transfer_origin_country_id, oco.name as transfer_origin_country_name, oco.code as transfer_origin_country_code,\n"
          + "       dco.id as transfer_destination_country_id, dco.name as transfer_destination_country_name, dco.code as transfer_destination_country_code\n"
          + "from transfers t\n"
          + "left join locations o on t.origin = o.id\n"
          + "left join locations d on t.destination = d.id\n"
          + "left join cities oc on oc.id = o.city_id\n"
          + "left join cities dc on dc.id = d.city_id\n"
          + "left join countries oco on oco.id = oc.country_id\n"
          + "left join countries dco on dco.id = dc.country_id\n"
          + "where origin = :originId\n"
          + "and destination = :destinationId\n"
          + "and date = :date";

  public Mono<ServerResponse> transfers(ServerRequest request) {
    var originId = Long.parseLong(request.queryParam("originId").orElseThrow(
        () -> new BadRequestException("Not correct origin id in request")));
    var destinationId = Long.parseLong(request.queryParam("destinationId").orElseThrow(
        () -> new BadRequestException("Not correct destination id in request")));
    var date = LocalDate.parse(request.queryParam("date")
                                      .orElseThrow(() -> new BadRequestException(
                                          "Not correct date in request")),
        DateTimeFormatter.ISO_DATE);

    var body = client.sql(TRANSFERS_BY_ORIGIN_AND_DESTINATION_AND_DATE)
                     .bind("originId", originId)
                     .bind("destinationId", destinationId)
                     .bind("date", date)
                     .map(transferMapper)
                     .all()
                     .onErrorComplete()
                     .mapNotNull(TransferResponse::from);

    return ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(body, TransferResponse.class)
        .switchIfEmpty(ServerResponse.notFound().build());

  }

}
