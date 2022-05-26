package by.vk.springbootwebnative.user.api.response;

import by.vk.springbootweb.transfer.api.response.TransferResponse;
import by.vk.springbootweb.user.repository.usertransfer.State;
import by.vk.springbootweb.user.repository.usertransfer.UsersTransfers;

public record UserTransferResponse(UserResponse user, TransferResponse transfer, State state, String description) {
    public static UserTransferResponse from(UsersTransfers entity) {
        return new UserTransferResponse(UserResponse.from(entity.getUser()), TransferResponse.from(entity.getTransfer()), entity.getState(), entity.getDescription());
    }
}
