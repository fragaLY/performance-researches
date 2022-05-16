package by.vk.datagen.data.user;

import by.vk.datagen.data.location.Location;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
