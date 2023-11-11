package com.sidof.task.security.auth;

import com.sidof.task.security.model.AuthenticationRequest;
import com.sidof.task.security.model.AuthenticationResponse;
import com.sidof.task.security.model.RegisterRequest;
import com.sidof.task.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * @Author sidof
 * @Since 10/4/23
 * @Version v1.0
 * @YouTube @sidof8065
 */
@RestController
@RequestMapping("api/v1/task/auth")
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
@RequiredArgsConstructor
public class Auth {
    private final UserService userService;
    private final LogoutHandler logoutHandler;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) throws InterruptedException {
        AuthenticationResponse registered = userService.register(registerRequest);
        return new ResponseEntity<AuthenticationResponse>(registered, CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse authenticate = userService.authenticate(authenticationRequest);
        return new ResponseEntity<AuthenticationResponse>(authenticate, OK);
    }


    @GetMapping(path = "/isTokenValid/{token}")
    public ResponseEntity<Boolean> isTokenValid(@PathVariable("token") String token) {
        return new ResponseEntity<Boolean>(userService.isTokenValid(token), OK);
    }
}
