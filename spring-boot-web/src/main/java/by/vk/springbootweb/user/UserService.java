package by.vk.springbootweb.user;

import by.vk.springbootweb.exception.NotFoundException;
import by.vk.springbootweb.transfer.repository.TransferRepository;
import by.vk.springbootweb.user.api.payload.UserEditionPayload;
import by.vk.springbootweb.user.api.payload.UserTransferCreationPayload;
import by.vk.springbootweb.user.api.payload.UserTransferEditionPayload;
import by.vk.springbootweb.user.api.response.UserResponse;
import by.vk.springbootweb.user.api.response.UserTransferResponse;
import by.vk.springbootweb.user.repository.user.UserRepository;
import by.vk.springbootweb.user.repository.usertransfer.State;
import by.vk.springbootweb.user.repository.usertransfer.UsersTransfers;
import by.vk.springbootweb.user.repository.usertransfer.UsersTransfersId;
import by.vk.springbootweb.user.repository.usertransfer.UsersTransfersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public record UserService(UserRepository userRepository,
                          UsersTransfersRepository usersTransfersRepository,
                          TransferRepository transferRepository) {

    public UserResponse user(Long userId) {
        log.info("[USER SERVICE] Retrieving user with id [{}]", userId);
        return UserResponse.from(userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found")));
    }

    public void user(Long userId, UserEditionPayload payload) {
        log.info("[USER SERVICE] Updating user with id [{}]", userId);
        var user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        user.setFirstName(payload.firstName());
        user.setLastName(payload.lastName());
        userRepository.save(user);
    }

    public List<UserTransferResponse> userTransfers(Long userId) {
        log.info("[USER SERVICE] Retrieving user's with id [{}] transfers", userId);
        return usersTransfersRepository.findByUserId(userId).parallelStream().map(UserTransferResponse::from).collect(Collectors.toList());
    }

    public UserTransferResponse userTransfer(Long userId, Long transferId) {
        log.info("[USER SERVICE] Retrieving user's with id [{}] transfer with id [{}]", userId, transferId);
        return UserTransferResponse.from(usersTransfersRepository.findByUserIdAndTransferId(userId, transferId).orElseThrow(() -> new NotFoundException("User's transfer not found")));
    }

    public void userTransfer(Long userId, Long transferId, UserTransferEditionPayload payload) {
        log.info("[USER SERVICE] Updating user's with id [{}] transfer with id [{}]", userId, transferId);
        var userTransfer = usersTransfersRepository.findByUserIdAndTransferId(userId, transferId).orElseThrow(() -> new NotFoundException("User's transfer not found"));
        userTransfer.setState(payload.state());
        userTransfer.setDescription(payload.description());
        usersTransfersRepository.save(userTransfer);
    }

    public void userTransfer(Long userId, Long transferId, UserTransferCreationPayload payload) {
        log.info("[USER SERVICE] Creating user's with id [{}] transfer with id [{}]", userId, transferId);
        var user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        var transfer = transferRepository.findById(transferId).orElseThrow(() -> new NotFoundException("Transfer not found"));
        var userTransfer = new UsersTransfers(new UsersTransfersId(user.getId(), transfer.getId()), user, transfer, State.BOOKED, payload.description());
        usersTransfersRepository.save(userTransfer);
    }
}
