package final_exam.ezechiel_jolie.GalacTicket.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import final_exam.ezechiel_jolie.GalacTicket.model.Auth;
import final_exam.ezechiel_jolie.GalacTicket.model.User;
import final_exam.ezechiel_jolie.GalacTicket.service.AuthenticationService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<Auth> register(@RequestBody User userRequest) {
        Auth response = authenticationService.register(userRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Auth> login(@RequestBody User userRequest) {
        Auth response = authenticationService.authenticate(userRequest);
        return ResponseEntity.ok(response);
    }
}

