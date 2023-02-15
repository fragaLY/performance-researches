package by.vk.user.transfers;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public record UserTransferCreationPayload(String description) {

}
