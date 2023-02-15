package by.vk.user.transfers;

import by.vk.transfer.TransferRepository;
import by.vk.user.UserRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.r2dbc.operations.R2dbcOperations;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.Status;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@R2dbcRepository(dialect = Dialect.POSTGRES)
interface Repository extends ReactiveStreamsCrudRepository<UsersTransfers, UsersTransfersId> {


  Flux<UsersTransfers> findByUserId(Long userId);

  @Override
  Mono<UsersTransfers> findById(UsersTransfersId id);

  @Override
  Mono<Void> update(UsersTransfers entity);

  @Override
  Mono<Void> save(UsersTransfers entity);
}

@Controller("/users")
public record UsersTransfersApi(Service service) {

  @Get("/{userId}/transfers")
  public Flux<UsersTransfers> all(Long userId) {
    return service.all(userId);
  }

  @Get("/{userId}/transfers/{transferId}")
  public Mono<UsersTransfers> one(Long userId, Long transferId) {
    return service.one(userId, transferId);
  }

  @Status(HttpStatus.NO_CONTENT)
  @Put("/{userId}/transfers/{transferId}")
  public Mono<Void> update(Long userId, Long transferId, @Body UserTransferEditionPayload payload) {
    return service.update(userId, transferId, payload);
  }

  @Status(HttpStatus.OK)
  @Post("/{userId}/transfers/{transferId}")
  public Mono<Void> create(Long userId, Long transferId,
      @Body UserTransferCreationPayload payload) {
    return service.create(userId, transferId, payload);
  }
}

@Singleton
class Service {

  private final Repository repository;
  private final TransferRepository transferRepository;
  private final UserRepository userRepository;
  private final R2dbcOperations operations;


  public Service(Repository repository, TransferRepository transferRepository,
      UserRepository userRepository, R2dbcOperations operations) {
    this.repository = repository;
    this.transferRepository = transferRepository;
    this.userRepository = userRepository;
    this.operations = operations;
  }

  Flux<UsersTransfers> all(Long userId) {
    return repository.findByUserId(userId);
  }

  Mono<UsersTransfers> one(Long userId, Long transferId) {
    return repository.findById(new UsersTransfersId(userId, transferId));
  }

  Mono<Void> update(Long userId, Long transferId, UserTransferEditionPayload payload) {
    return Mono.from(operations.withTransaction(
        status -> repository.findById(new UsersTransfersId(userId, transferId))
                            .flatMap(it -> {
                              it.setState(payload.state());
                              it.setDescription(payload.description());
                              return repository.update(it);
                            })
                            .then()));
  }

  public Mono<Void> create(Long userId, Long transferId, UserTransferCreationPayload payload) {
    return Mono.from(operations.withTransaction(
        status -> userRepository.findById(userId)
                                .switchIfEmpty(
                                    Mono.error(new RuntimeException("Not correct user selected.")))
                                .flatMap(it -> transferRepository.findById(transferId)
                                                                 .switchIfEmpty(
                                                                     Mono.error(
                                                                         new RuntimeException(
                                                                             "Not correct transfer selected.")))
                                                                 .flatMap(that -> repository.save(
                                                                     new UsersTransfers(
                                                                         new UsersTransfersId(
                                                                             it.getId(),
                                                                             that.getId()),
                                                                         it, that, State.BOOKED,
                                                                         payload.description()))))
                                .then()));
  }
}