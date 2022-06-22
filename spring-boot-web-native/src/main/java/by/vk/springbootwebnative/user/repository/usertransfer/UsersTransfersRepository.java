package by.vk.springbootwebnative.user.repository.usertransfer;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UsersTransfersRepository extends CrudRepository<UsersTransfers, Long> {

  List<UsersTransfers> findByUserId(Long userId);

  Optional<UsersTransfers> findByUserIdAndTransferId(Long userId, Long transferId);
}
