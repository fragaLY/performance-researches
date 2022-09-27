package by.vk.springbootreactivenative.user.payload;

import by.vk.springbootreactivenative.user.repository.usertransfer.State;

public record UserTransferEditionPayload(State state, String description) {

}
