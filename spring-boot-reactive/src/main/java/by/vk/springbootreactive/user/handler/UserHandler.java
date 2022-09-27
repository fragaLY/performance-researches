package by.vk.springbootreactive.user.handler;

import by.vk.springbootreactive.exception.BadRequestException;
import by.vk.springbootreactive.user.mapper.UserTransfersMapper;
import by.vk.springbootreactive.user.payload.UserEditionPayload;
import by.vk.springbootreactive.user.payload.UserTransferCreationPayload;
import by.vk.springbootreactive.user.payload.UserTransferEditionPayload;
import by.vk.springbootreactive.user.repository.user.UserRepository;
import by.vk.springbootreactive.user.repository.usertransfer.State;
import by.vk.springbootreactive.user.responses.UserResponse;
import by.vk.springbootreactive.user.responses.UserTransferResponse;
import org.springframework.http.MediaType;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public record UserHandler(DatabaseClient client, UserRepository userRepository,
                          UserTransfersMapper userTransfersMapper) {

  private static final String USER_TRANSFERS_BY_USER_QUERY_VALUE =
      "select u.id as user_id, u.first_name as user_first_name, u.last_name as user_last_name, u.email as user_email, u.phone as user_phone,\n"
          + "       t.id as transfer_id, t.origin as transfer_origin_id, t.destination as transfer_destination_id, t.capacity as transfer_capacity, t.date as transfer_date, t.duration as transfer_duration, t.price::numeric  as transfer_price, t.description as transfer_description,\n"
          + "       o.id as transfer_origin_id, o.location as transfer_origin_location, d.id as transfer_destination_id, d.location as transfer_destination_location,\n"
          + "       dc.id as transfer_destination_city_id, dc.name as transfer_destination_city_name, dc.code as transfer_destination_city_code,\n"
          + "       oc.id as transfer_origin_city_id, oc.name as transfer_origin_city_name, oc.code as transfer_origin_city_code,\n"
          + "       oco.id as transfer_origin_country_id, oco.name as transfer_origin_country_name, oco.code as transfer_origin_country_code,\n"
          + "       dco.id as transfer_destination_country_id, dco.name as transfer_destination_country_name, dco.code as transfer_destination_country_code\n,"
          + "       ut.state as state,  ut.description as description\n"
          + "from users_transfers ut\n" + "join users u on ut.user_id = u.id\n"
          + "join transfers t on t.id = ut.transfer_id\n"
          + "left join locations o on t.origin = o.id\n"
          + "left join locations d on t.destination = d.id\n"
          + "left join cities oc on oc.id = o.city_id\n"
          + "left join cities dc on dc.id = d.city_id\n"
          + "left join countries oco on oco.id = oc.country_id\n"
          + "left join countries dco on dco.id = dc.country_id\n" + "where user_id = :userId";

  private static final String CREATE_USER_TRANSFER_QUERY_VALUE = "INSERT INTO users_transfers (user_id, transfer_id, state, description) VALUES (:userId, :transferId, :state, :description)";
  private static final String UPDATE_USER_TRANSFER_QUERY_VALUE = "UPDATE users_transfers SET state = :state, description = :description WHERE user_id = :userId AND transfer_id = :transferId";
  private static final String UPDATE_USER_QUERY_VALUE = "UPDATE users SET first_name = :firstName, last_name = :lastName WHERE id = :userId";

  public Mono<ServerResponse> user(ServerRequest request) {
    var userId = Long.parseLong(request.pathVariable("userId"));
    var body = userRepository.findById(userId).mapNotNull(UserResponse::from).onErrorComplete();
    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                         .body(body, UserResponse.class)
                         .switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> updateUser(ServerRequest request) {
    var userId = Long.parseLong(request.pathVariable("userId"));
    return request.bodyToMono(UserEditionPayload.class).flatMap(
                      it -> client.sql(UPDATE_USER_QUERY_VALUE).bind("userId", userId)
                                  .bind("firstName", it.firstName()).bind("lastName", it.lastName()).fetch()
                                  .rowsUpdated().onErrorComplete())
                  .flatMap(it -> ServerResponse.noContent().build()).switchIfEmpty(
            Mono.error(new BadRequestException("User payload had not been found")));
  }

  public Mono<ServerResponse> userTransfer(ServerRequest request) {
    var userId = Long.parseLong(request.pathVariable("userId"));
    var transferId = Long.parseLong(request.pathVariable("transferId"));

    var sql = USER_TRANSFERS_BY_USER_QUERY_VALUE + " AND transfer_id = :transferId";

    var body = client.sql(sql).bind("userId", userId).bind("transferId", transferId)
                     .map(userTransfersMapper).one().mapNotNull(UserTransferResponse::from)
                     .onErrorComplete();

    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                         .body(body, UserTransferResponse.class)
                         .switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> userTransfers(ServerRequest request) {
    var userId = Long.parseLong(request.pathVariable("userId"));
    var body = client.sql(USER_TRANSFERS_BY_USER_QUERY_VALUE).bind("userId", userId)
                     .map(userTransfersMapper).all().mapNotNull(UserTransferResponse::from)
                     .onErrorComplete();

    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                         .body(body, UserTransferResponse.class)
                         .switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> createUserTransfer(ServerRequest request) {
    var userId = Long.parseLong(request.pathVariable("userId"));
    var transferId = Long.parseLong(request.pathVariable("transferId"));

    return request.bodyToMono(UserTransferCreationPayload.class).switchIfEmpty(
                      Mono.error(new BadRequestException("User's transfer payload had not been found"))).flatMap(
                      it -> client.sql(CREATE_USER_TRANSFER_QUERY_VALUE).bind("userId", userId)
                                  .bind("transferId", transferId).bind("state", State.BOOKED.toString())
                                  .bind("description", it.description()).fetch().first().onErrorComplete())
                  .flatMap(it -> ServerResponse.noContent().build());

  }


  public Mono<ServerResponse> updateUserTransfer(ServerRequest request) {
    var userId = Long.parseLong(request.pathVariable("userId"));
    var transferId = Long.parseLong(request.pathVariable("transferId"));

    return request.bodyToMono(UserTransferEditionPayload.class).flatMap(
                      it -> client.sql(UPDATE_USER_TRANSFER_QUERY_VALUE).bind("userId", userId)
                                  .bind("transferId", transferId).bind("state", it.state().toString())
                                  .bind("description", it.description()).fetch().rowsUpdated().onErrorComplete())
                  .flatMap(it -> ServerResponse.noContent().build())
                  .switchIfEmpty(Mono.error(
                      new BadRequestException("User's transfer payload had not been found")));
  }

}
