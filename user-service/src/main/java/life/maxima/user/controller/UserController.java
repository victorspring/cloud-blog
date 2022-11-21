package life.maxima.user.controller;

import life.maxima.user.dto.UserCreateDto;
import life.maxima.user.dto.UserDto;
import life.maxima.user.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final KeycloakService keycloakService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable String id){
        return ResponseEntity.ok(new UserDto(keycloakService.getUsername(id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UserCreateDto userDto){
        keycloakService.create(userDto.getUsername(), userDto.getPassword());
    }
}
