package by.vk.springbootweb.user.repository.usertransfer;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UsersTransfersRepository extends CrudRepository<UsersTransfers, Long> {

    List<UsersTransfers> findByUserId(Long userId);

    Optional<UsersTransfers> findByUserIdAndTransferId(Long userId, Long transferId);
}
