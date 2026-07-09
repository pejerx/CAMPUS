package edu.cit.garol.campus.admin;

import edu.cit.garol.campus.itemreport.ItemReport;
import edu.cit.garol.campus.itemreport.ItemReportRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    private final ItemReportRepository itemReportRepository;

    public AdminController(ItemReportRepository itemReportRepository) {
        this.itemReportRepository = itemReportRepository;
    }

    @GetMapping("/reports")
    public ResponseEntity<List<ItemReport>> getAllReports() {
        return ResponseEntity.ok(itemReportRepository.findAll());
    }

    @GetMapping("/reported-items")
    public ResponseEntity<List<ItemReport>> getReportedItems() {
        return ResponseEntity.ok(itemReportRepository.findAll());
    }

    @PutMapping("/reports/{id}/status")
    public ResponseEntity<ItemReport> updateReportStatus(
            @PathVariable String id,
            @RequestBody AdminStatusRequest request
    ) {
        ItemReport report = itemReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item report not found"));

        report.setStatus(request.getStatus());

        ItemReport updatedReport = itemReportRepository.save(report);

        return ResponseEntity.ok(updatedReport);
    }
}