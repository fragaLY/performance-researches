package by.vk.springbootwebnative.transfer.repository;


import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface TransferRepository extends CrudRepository<Transfer, Long> {
    List<Transfer> findByOriginIdAndDestinationIdAndDate(Long originId, Long destinationId, Date date);
}
