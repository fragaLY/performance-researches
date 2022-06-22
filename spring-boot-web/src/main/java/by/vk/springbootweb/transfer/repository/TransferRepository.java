package by.vk.springbootweb.transfer.repository;


import java.util.Date;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface TransferRepository extends CrudRepository<Transfer, Long> {

  List<Transfer> findByOriginIdAndDestinationIdAndDate(Long originId, Long destinationId,
      Date date);
}
