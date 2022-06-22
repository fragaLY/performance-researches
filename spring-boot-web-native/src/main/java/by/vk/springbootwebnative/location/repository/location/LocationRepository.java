package by.vk.springbootwebnative.location.repository.location;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {

  List<Location> findByCityId(Long cityId);
}
