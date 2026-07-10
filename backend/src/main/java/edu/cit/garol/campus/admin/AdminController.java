package edu.cit.garol.campus.admin;

import edu.cit.garol.campus.auth.AdminLoginResponse;
import edu.cit.garol.campus.itemreport.ItemReport;
import edu.cit.garol.campus.itemreport.ItemReportRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    private static final String ADMIN_ID = "1";
    private static final String ADMIN_PASSWORD = "school-admin-access";

    private final ItemReportRepository itemReportRepository;

    public AdminController(ItemReportRepository itemReportRepository) {
        this.itemReportRepository = itemReportRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(
            @RequestBody AdminLoginRequest request
    ) {
        if (request == null) {
            return ResponseEntity
                    .badRequest()
                    .body("Login request is required.");
        }

        String emailOrId = request.getEmailOrId();
        String password = request.getPassword();

        if (emailOrId == null || emailOrId.isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body("Administrator ID is required.");
        }

        if (password == null || password.isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body("Administrator password is required.");
        }

        boolean correctId = ADMIN_ID.equals(emailOrId.trim());
        boolean correctPassword = ADMIN_PASSWORD.equals(password);

        if (!correctId || !correctPassword) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Incorrect administrator ID or password.");
        }

        AdminLoginResponse response = new AdminLoginResponse(
                ADMIN_ID,
                "Administrator",
                "ADMIN",
                "/admin-dashboard"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/reports")
    public ResponseEntity<List<ItemReport>> getAllReports() {
        return ResponseEntity.ok(
                itemReportRepository.findAll()
        );
    }

    @GetMapping("/reported-items")
    public ResponseEntity<List<ItemReport>> getReportedItems() {
        return ResponseEntity.ok(
                itemReportRepository.findAll()
        );
    }

    @PutMapping("/reports/{id}/status")
    public ResponseEntity<ItemReport> updateReportStatus(
            @PathVariable String id,
            @RequestBody AdminStatusRequest request
    ) {
        ItemReport report = itemReportRepository
                .findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Item report not found"
                        )
                );

        report.setStatus(request.getStatus());

        ItemReport updatedReport =
                itemReportRepository.save(report);

        return ResponseEntity.ok(updatedReport);
    }
}