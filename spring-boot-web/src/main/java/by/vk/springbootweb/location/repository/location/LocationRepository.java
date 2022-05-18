package by.vk.springbootweb.location.repository.location;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationRepository extends CrudRepository<Location, Long> {
    List<Location> findByCityId(Long cityId);
}
