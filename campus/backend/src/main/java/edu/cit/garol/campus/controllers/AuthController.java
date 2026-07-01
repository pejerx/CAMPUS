import com.example.campusbackend.dto.LoginRequest;
import com.example.campusbackend.dto.RegisterRequest;
import com.example.campusbackend.entities.User;
import com.example.campusbackend.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
                request.email,
                request.password
        );

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
}