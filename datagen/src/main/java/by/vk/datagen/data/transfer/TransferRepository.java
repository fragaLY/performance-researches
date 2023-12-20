package by.vk.datagen.data.transfer;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface TransferRepository extends CrudRepository<Transfer, Long> {

  @Transactional
  @Modifying
  @Query(value = """
      INSERT INTO a2b.transfers (id, origin, destination, capacity, date, duration, price, description) VALUES
      (:id, :origin, :destination, :capacity, :date, tsrange(:startDateTime, :endDateTime), :price, :description)
      """, nativeQuery = true)
  void create(Long id, Long origin, Long destination, Short capacity, Date date,
      LocalDateTime startDateTime, LocalDateTime endDateTime, BigDecimal price, String description);
}
