package edu.cit.garol.campus.controller;

import edu.cit.garol.campus.entity.ItemReport;
import edu.cit.garol.campus.repository.ItemReportRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "http://localhost:5173")
public class ItemReportController {

    private final ItemReportRepository itemReportRepository;

    public ItemReportController(ItemReportRepository itemReportRepository) {
        this.itemReportRepository = itemReportRepository;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ItemReport> createReport(
            @RequestParam String userId,
            @RequestParam String itemName,
            @RequestParam String category,
            @RequestParam String description,
            @RequestParam String lastSeenLocation,
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
            report.setItemName(itemName);
            report.setCategory(category);
            report.setDescription(description);
            report.setLastSeenLocation(lastSeenLocation);
            report.setImagePath(imagePath);
            report.setStatus("Pending");

            ItemReport savedReport = itemReportRepository.save(report);

            return ResponseEntity.ok(savedReport);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}