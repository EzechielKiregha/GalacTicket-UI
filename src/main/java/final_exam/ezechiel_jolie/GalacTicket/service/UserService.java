package final_exam.ezechiel_jolie.GalacTicket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import final_exam.ezechiel_jolie.GalacTicket.feedback.Feedback;
import final_exam.ezechiel_jolie.GalacTicket.model.User;
import final_exam.ezechiel_jolie.GalacTicket.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Feedback saveUser(User user) {
        if (user == null) {
            return new Feedback("Invalid user data");
        }

        if (user.getId() != null && userRepository.existsById(user.getId())) {
            return new Feedback("User with ID " + user.getId() + " already exists");
        }

        User savedUser = userRepository.save(user);
        if (savedUser != null) {
            return new Feedback("User saved successfully");
        }
        return new Feedback("Failed to save user");
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(String Username) {
        return userRepository.findByUsername(Username);
    }

    public Feedback updateUser(Long id, User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return new Feedback("User not found");
        }

        User existingUser = optionalUser.get();
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setRole(updatedUser.getRole());

        userRepository.save(existingUser);
        return new Feedback("User updated successfully");
    }

    public Feedback deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            return new Feedback("User not found");
        }

        userRepository.deleteById(id);
        return new Feedback("User deleted successfully");
    }
    // public Feedback authenticateUser(String email, String password) {
    //     User user = userRepository.findByEmail(email);
    //     if (user != null && user.getPassword().equals(password)) {
    //         return new Feedback("Authenticated");
    //     } else {
    //         return new Feedback("Invalid email or password");
    //     }
    // }
    public Feedback loginUser(User user) {
        User userlogin = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());

        if (userlogin == null) {
            return new Feedback("Error: Invalid email or password");
        }
        return new Feedback("Login successful");
    }
}


