package by.vk.springbootreactive.user.responses;

import by.vk.springbootreactive.transfer.responses.TransferResponse;
import by.vk.springbootreactive.user.repository.usertransfer.State;
import by.vk.springbootreactive.user.repository.usertransfer.UsersTransfers;

public record UserTransferResponse(UserResponse user, TransferResponse transfer, State state,
                                   String description) {

  public static UserTransferResponse from(UsersTransfers entity) {
    return new UserTransferResponse(UserResponse.from(entity.getUser()),
        TransferResponse.from(entity.getTransfer()), entity.getState(), entity.getDescription());
  }
}
