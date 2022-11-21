package life.maxima.common.entity.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@RedisHash
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    private String id;
    private String username;
}
