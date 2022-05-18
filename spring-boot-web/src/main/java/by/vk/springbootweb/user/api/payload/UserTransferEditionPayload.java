package by.vk.springbootweb.user.api.payload;

import by.vk.springbootweb.user.repository.usertransfer.State;

public record UserTransferEditionPayload(State state, String description) {
}
