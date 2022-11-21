package life.maxima.blog.service;

import life.maxima.common.entity.cache.User;
import life.maxima.common.repository.cache.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRemoteService implements UserService {

    @Value("${user-service.endpoint}")
    private String usersEndpoint;

    private final RestOperations restOperations;
    private final UserRepository userRepository;

    @Override
    public String getUsername(String id) {
        return userRepository.findById(id).orElseGet(
                () -> restOperations.getForObject(usersEndpoint + "/" + id, User.class)).getUsername();
    }
}
