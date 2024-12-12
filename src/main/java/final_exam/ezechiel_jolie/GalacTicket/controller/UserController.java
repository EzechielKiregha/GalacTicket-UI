package final_exam.ezechiel_jolie.GalacTicket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import final_exam.ezechiel_jolie.GalacTicket.feedback.Feedback;
import final_exam.ezechiel_jolie.GalacTicket.model.Auth;
import final_exam.ezechiel_jolie.GalacTicket.model.Login;
import final_exam.ezechiel_jolie.GalacTicket.model.User;
import final_exam.ezechiel_jolie.GalacTicket.service.AuthenticationService;
import final_exam.ezechiel_jolie.GalacTicket.service.UserService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    private final AuthenticationService authService;

    public UserController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/save")
    public ResponseEntity<Auth> register(
            @RequestBody User request
            ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Feedback> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        Feedback responseMessage = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(responseMessage);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Feedback> deleteUser(@PathVariable Long id) {
        Feedback responseMessage = userService.deleteUser(id);
        return ResponseEntity.ok(responseMessage);
    }

    @PostMapping("/login")
    public ResponseEntity<Login> login(@RequestBody User request) {
        Auth authResponse = authService.authenticate(request);
        Optional<User> user = userService.findByUsername(request.getUsername());
        
        Login login = new Login(authResponse, user.get());
        return ResponseEntity.ok(login);
    }
}

