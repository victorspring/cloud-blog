package life.maxima.user.service;

import life.maxima.common.entity.cache.User;
import life.maxima.common.repository.cache.UserRepository;
import life.maxima.user.dto.CredentialsDto;
import life.maxima.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    @Value("${admin.username}")
    private String username;

    @Value("${admin.password}")
    private String password;

    @Value("${keycloak.url}")
    private String baseUrl;

    @Value("${keycloak.token.endpoint}")
    private String tokenEndpoint;

    @Value("${keycloak.users}")
    private String usersEndpoint;


    private final RestOperations restOperations;

    private final UserRepository userRepository;

    public String getUsername(String id){
        return findUserById(id).orElseThrow().getUsername();
    }

    public Optional<UserDto> findUserById(String id){
        return findUserByCondition(user -> Objects.equals(id, user.getId()));
    }

    public Optional<UserDto> findUserByUsername(String username){
        return findUserByCondition(user -> Objects.equals(username, user.getUsername()));
    }

    private Optional<UserDto> findUserByCondition(Predicate<UserDto> predicate){
        Optional<UserDto> result = Optional.empty();
        for (UserDto user : getAllUsers(getTokenForAdmin())){
            userRepository.save(new User(user.getId(), user.getUsername()));
            if (predicate.test(user)) {
                result = Optional.of(user);
            }
        }
        return result;
    }

    private List<UserDto> getAllUsers(String token) {
        return restOperations.exchange(baseUrl + usersEndpoint,
                HttpMethod.GET,
                new HttpEntity<>(authorization(token)),
                new ParameterizedTypeReference<List<UserDto>>() {}
        ).getBody();
    }

    private HttpHeaders authorization(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        return headers;
    }

    private String getTokenForAdmin(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username",username);
        map.add("password",password);
        map.add("grant_type","password");
        map.add("client_id","admin-cli");

        ResponseEntity<Map<String, String>> response =
                restOperations.exchange(baseUrl + tokenEndpoint,
                        HttpMethod.POST,
                        new HttpEntity<>(map, headers),
                        new ParameterizedTypeReference<>() {
                        });
        return response.getBody().get("access_token");
    }


    public void create(String username, String password) {
        CredentialsDto credentialsDto = new CredentialsDto(
                false, "password", password);
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setEnabled(true);
        userDto.setCredentials(List.of(credentialsDto));

        String adminToken = getTokenForAdmin();

        restOperations.postForEntity(baseUrl + usersEndpoint,
                new HttpEntity<>(userDto, authorization(adminToken)), Void.class);

        String id = findUserByUsername(username).orElseThrow().getId();
        userRepository.save(new User(id, username));
    }
}
