package edu.cit.garol.campus.itemreport;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ItemReportController {

    private final ItemReportRepository itemReportRepository;

    public ItemReportController(ItemReportRepository itemReportRepository) {
        this.itemReportRepository = itemReportRepository;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ItemReport> createReport(
            @RequestParam String userId,
            @RequestParam String reportType,
            @RequestParam String itemName,
            @RequestParam String category,
            @RequestParam String description,
            @RequestParam String location,
            @RequestParam(required = false) MultipartFile image
    ) {
        try {
            String imagePath = null;

            if (image != null && !image.isEmpty()) {
                String uploadDir = "uploads/";
                Files.createDirectories(Paths.get(uploadDir));

                String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);

                Files.write(filePath, image.getBytes());

                imagePath = uploadDir + fileName;
            }

            ItemReport report = new ItemReport();
            report.setUserId(userId);
            report.setReportType(reportType);
            report.setItemName(itemName);
            report.setCategory(category);
            report.setDescription(description);
            report.setLocation(location);
            report.setImagePath(imagePath);

            // User-submitted reports should NOT be public immediately.
            report.setStatus("Under Review");

            ItemReport savedReport = itemReportRepository.save(report);

            return ResponseEntity.ok(savedReport);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ItemReport>> getAllReports() {
        return ResponseEntity.ok(itemReportRepository.findAll());
    }

    @GetMapping("/public")
    public ResponseEntity<List<ItemReport>> getPublicReports() {
        List<String> publicStatuses = List.of(
                "Unclaimed",
                "Pending Claim",
                "Claimed"
        );

        return ResponseEntity.ok(itemReportRepository.findByStatusIn(publicStatuses));
    }

    @GetMapping("/uploads/{fileName}")
    public ResponseEntity<Resource> getUploadedImage(@PathVariable String fileName) {
        try {
            Path imagePath = Paths.get("uploads").resolve(fileName);
            Resource resource = new UrlResource(imagePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}