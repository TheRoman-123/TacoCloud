package sia.tacocloud.repository;

import org.springframework.data.repository.CrudRepository;
import sia.tacocloud.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
