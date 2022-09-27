package by.vk.springbootreactivenative.user.responses;

import by.vk.springbootreactivenative.transfer.responses.TransferResponse;
import by.vk.springbootreactivenative.user.repository.usertransfer.State;
import by.vk.springbootreactivenative.user.repository.usertransfer.UsersTransfers;

public record UserTransferResponse(UserResponse user, TransferResponse transfer, State state,
                                   String description) {

  public static UserTransferResponse from(UsersTransfers entity) {
    return new UserTransferResponse(UserResponse.from(entity.getUser()),
        TransferResponse.from(entity.getTransfer()), entity.getState(), entity.getDescription());
  }
}
