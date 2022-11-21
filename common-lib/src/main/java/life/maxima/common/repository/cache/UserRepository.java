package life.maxima.common.repository.cache;

import life.maxima.common.entity.cache.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,String> {
}
