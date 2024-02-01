package az.unitech.msuser.service;

import az.unitech.msuser.entity.UserEntity;
import az.unitech.msuser.exception.InvalidCredentialsException;
import az.unitech.msuser.exception.UserAlreadyRegisteredException;
import az.unitech.msuser.exception.UserNotFoundException;
import az.unitech.msuser.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final String PIN = "123456";
    private static final String PASSWORD = "password123";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void registerUser_Success() {
        when(userRepository.findByPin(PIN)).thenReturn(Optional.empty());
        ResponseEntity<Void> response = userService.registerUser(PIN);

        verify(userRepository, times(1)).findByPin(PIN);
        verify(userRepository, times(1)).save(any(UserEntity.class));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void registerUser_UserAlreadyRegistered() {

        when(userRepository.findByPin(PIN)).thenReturn(Optional.of(new UserEntity()));

        assertThrows(UserAlreadyRegisteredException.class, () -> userService.registerUser(PIN));
        verify(userRepository, times(1)).findByPin(PIN);
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void loginUser_Success() {
        UserEntity userEntity = new UserEntity();
        userEntity.setPin(PIN);
        userEntity.setPassword(PASSWORD);
        when(userRepository.findByPin(PIN)).thenReturn(Optional.of(userEntity));

        ResponseEntity<Void> response = userService.loginUser(PIN, PASSWORD);

        verify(userRepository, times(1)).findByPin(PIN);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void loginUser_dataNotFound() {

        when(userRepository.findByPin(PIN)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.loginUser(PIN, PASSWORD));
        verify(userRepository, times(1)).findByPin(PIN);
    }

    @Test
    void loginUser_InvalidCredentials() {
        String password = "3214";
        UserEntity userEntity = new UserEntity();
        userEntity.setPin(PIN);
        userEntity.setPassword(PASSWORD);
        when(userRepository.findByPin(PIN)).thenReturn(Optional.of(userEntity));

        assertThrows(InvalidCredentialsException.class, () -> userService.loginUser(PIN, password));
        verify(userRepository, times(1)).findByPin(PIN);
    }

}