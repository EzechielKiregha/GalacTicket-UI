package final_exam.ezechiel_jolie.GalacTicket.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import final_exam.ezechiel_jolie.GalacTicket.model.Authentication;
import final_exam.ezechiel_jolie.GalacTicket.model.User;
import final_exam.ezechiel_jolie.GalacTicket.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<Authentication> register(@RequestBody User userRequest) {
        Authentication response = authenticationService.register(userRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Authentication> login(@RequestBody User userRequest) {
        Authentication response = authenticationService.authenticate(userRequest);
        return ResponseEntity.ok(response);
    }
}

