package az.unitech.msuser.service;

import az.unitech.msuser.entity.UserEntity;
import az.unitech.msuser.exception.InvalidCredentialsException;
import az.unitech.msuser.exception.UserAlreadyRegisteredException;
import az.unitech.msuser.exception.UserNotFoundException;
import az.unitech.msuser.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public ResponseEntity<Void> registerUser(String pin) {
        Optional<UserEntity> userEntity = userRepository.findByPin(pin);
        if (userEntity.isPresent()) {
            throw new UserAlreadyRegisteredException("User is already registered!");
        }
        UserEntity user = new UserEntity();
        user.setPin(pin);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<Void> loginUser(String pin, String password) {
        UserEntity userEntity = userRepository.findByPin(pin)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!userEntity.getPassword().equals(password)) {
            throw new InvalidCredentialsException("Invalid credentials. Please try again.");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
