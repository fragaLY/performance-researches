package by.vk.springbootwebnative.user.api.payload;


import by.vk.springbootwebnative.user.repository.usertransfer.State;

public record UserTransferEditionPayload(State state, String description) {

}
