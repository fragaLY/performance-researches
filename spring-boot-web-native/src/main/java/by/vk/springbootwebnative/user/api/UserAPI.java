package by.vk.springbootwebnative.user.api;

import by.vk.springbootwebnative.user.UserService;
import by.vk.springbootwebnative.user.api.payload.UserEditionPayload;
import by.vk.springbootwebnative.user.api.payload.UserTransferCreationPayload;
import by.vk.springbootwebnative.user.api.payload.UserTransferEditionPayload;
import by.vk.springbootwebnative.user.api.response.UserResponse;
import by.vk.springbootwebnative.user.api.response.UserTransferResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public record UserAPI(UserService service) {

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse user(
            @NotNull(message = "The id of user should not be null") @PathVariable Long userId) {
        return service.user(userId);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@NotNull(message = "The id of user should not be null") @PathVariable Long userId,
                @Valid @RequestBody UserEditionPayload payload) {
        service.user(userId, payload);
    }

    @GetMapping("/{userId}/transfers")
    @ResponseStatus(HttpStatus.OK)
    public List<UserTransferResponse> userTransfers(
            @NotNull(message = "The id of user should not be null") @PathVariable Long userId) {
        return service.userTransfers(userId);
    }

    @GetMapping("/{userId}/transfers/{transferId}")
    @ResponseStatus(HttpStatus.OK)
    public UserTransferResponse userTransfer(
            @NotNull(message = "The id of user should not be null") @PathVariable Long userId,
            @NotNull(message = "The id of transfer should not be null") @PathVariable Long transferId) {
        return service.userTransfer(userId, transferId);
    }

    @PostMapping("/{userId}/transfers/{transferId}")
    @ResponseStatus(HttpStatus.CREATED)
    void create(@NotNull(message = "The id of user should not be null") @PathVariable Long userId,
                @NotNull(message = "The id of transfer should not be null") @PathVariable Long transferId,
                @Valid @RequestBody UserTransferCreationPayload payload) {
        service.userTransfer(userId, transferId, payload);
    }

    @PutMapping("/{userId}/transfers/{transferId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@NotNull(message = "The id of user should not be null") @PathVariable Long userId,
                @NotNull(message = "The id of transfer should not be null") @PathVariable Long transferId,
                @Valid @RequestBody UserTransferEditionPayload payload) {
        service.userTransfer(userId, transferId, payload);
    }
}
