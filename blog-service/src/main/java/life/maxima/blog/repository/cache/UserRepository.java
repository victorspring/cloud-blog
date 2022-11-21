package life.maxima.blog.repository.cache;


import life.maxima.common.entity.cache.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,String> {
}
