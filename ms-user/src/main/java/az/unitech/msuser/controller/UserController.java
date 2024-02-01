package az.unitech.msuser.controller;

import az.unitech.msuser.model.LoginDto;
import az.unitech.msuser.model.UserDto;
import az.unitech.msuser.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody UserDto request) {
        String pin = request.getPin();
        return userService.registerUser(pin);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> loginUser(@RequestBody LoginDto request) {
        String pin = request.getPin();
        String password = request.getPassword();
        return userService.loginUser(pin, password);
    }


}
