package edu.cit.garol.campus.controllers;

import edu.cit.garol.campus.dto.LoginRequest;
import edu.cit.garol.campus.dto.RegisterRequest;
import edu.cit.garol.campus.entity.User;
import edu.cit.garol.campus.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.email);

        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("Email is already registered.");
        }

        User user = new User(
                request.firstName,
                request.lastName,
                request.role,
                request.contactNumber,
                request.email,
                request.password
        );

        user.setId(generateUniqueUserId());

        User savedUser = userRepository.save(user);

        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userByEmail = userRepository.findByEmail(request.id);
        Optional<User> userById = userRepository.findById(request.id);

        Optional<User> user = userByEmail.isPresent() ? userByEmail : userById;

        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found.");
        }

        if (!user.get().getPassword().equals(request.password)) {
            return ResponseEntity.badRequest().body("Incorrect password.");
        }

        return ResponseEntity.ok(user.get());
    }

    private String generateUniqueUserId() {
        Random random = new Random();
        String year = String.valueOf(Year.now().getValue()).substring(2);

        String generatedId;

        do {
            int fourDigits = 1000 + random.nextInt(9000);
            int threeDigits = 100 + random.nextInt(900);

            generatedId = year + "-" + fourDigits + "-" + threeDigits;

        } while (userRepository.existsById(generatedId));

        return generatedId;
    }
}