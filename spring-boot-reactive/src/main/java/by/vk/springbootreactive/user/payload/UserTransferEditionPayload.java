package by.vk.springbootreactive.user.payload;

import by.vk.springbootreactive.user.repository.usertransfer.State;

public record UserTransferEditionPayload(State state, String description) {

}
