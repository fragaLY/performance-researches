package by.vk.springbootreactivenative.user.repository.usertransfer;

import java.util.List;
import java.util.Optional;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UsersTransfersRepository extends R2dbcRepository<UsersTransfers, Long> {

  List<UsersTransfers> findByUserId(Long userId);

  Optional<UsersTransfers> findByUserIdAndTransferId(Long userId, Long transferId);
}
