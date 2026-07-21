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
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/reports")
public class ItemReportController {

    private final ItemReportRepository itemReportRepository;

    public ItemReportController(
            ItemReportRepository itemReportRepository
    ) {
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

                String originalFileName = image.getOriginalFilename();

                if (originalFileName == null ||
                        originalFileName.isBlank()) {

                    originalFileName = "uploaded-image";
                }

                String fileName =
                        UUID.randomUUID() + "_" + originalFileName;

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
            report.setCreatedAt(LocalDateTime.now());
            report.setStatus("Under Review");

            ItemReport savedReport =
                    itemReportRepository.save(report);

            return ResponseEntity.ok(savedReport);

        } catch (Exception exception) {
            exception.printStackTrace();

            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ItemReport>> getAllReports() {
        return ResponseEntity.ok(
                itemReportRepository.findAll()
        );
    }

    @GetMapping("/public")
        public ResponseEntity<List<ItemReport>> getPublicReports() {

        List<String> publicStatuses = List.of(
                "Unclaimed",
                "Pending Claim",
                "Claimed"
        );

        return ResponseEntity.ok(
                itemReportRepository.findByStatusIn(publicStatuses)
        );
        }

        /*
        * User - View only their own reports.
        */
        @GetMapping("/user/{userId}")
        public ResponseEntity<List<ItemReport>> getUserReports(
                @PathVariable String userId
        ) {

        return ResponseEntity.ok(
                itemReportRepository.findByUserId(userId)
        );

        }

        /*
        * ==========================================================
        * USER - UPDATE OWN REPORT
        * ==========================================================
        */

        @PutMapping("/{id}")
        public ResponseEntity<ItemReport> updateReport(
                @PathVariable Integer id,
                @RequestBody ItemReport updatedReport
        ) {

        return itemReportRepository.findById(id)
                .map(report -> {

                        report.setItemName(updatedReport.getItemName());
                        report.setCategory(updatedReport.getCategory());
                        report.setDescription(updatedReport.getDescription());
                        report.setLocation(updatedReport.getLocation());

                        ItemReport savedReport =
                                itemReportRepository.save(report);

                        return ResponseEntity.ok(savedReport);

                })
                .orElse(ResponseEntity.notFound().build());

        }

        /*
        * ==========================================================
        * USER - DELETE OWN REPORT
        * ==========================================================
        */

        @DeleteMapping("/{id}")
        public ResponseEntity<?> deleteReport(
                @PathVariable Integer id
        ) {

        ItemReport report = itemReportRepository
                .findById(id)
                .orElse(null);

        if (report == null) {
                return ResponseEntity.notFound().build();
        }

        itemReportRepository.delete(report);

        return ResponseEntity.ok().build();

        }

    @GetMapping("/uploads/{fileName:.+}")
    public ResponseEntity<Resource> getUploadedImage(
            @PathVariable String fileName
    ) {
        try {
            Path imagePath = Paths.get("uploads")
                    .resolve(fileName)
                    .toAbsolutePath()
                    .normalize();

            Resource resource =
                    new UrlResource(imagePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            String contentType =
                    Files.probeContentType(imagePath);

            MediaType mediaType =
                    MediaType.APPLICATION_OCTET_STREAM;

            if (contentType != null) {
                mediaType =
                        MediaType.parseMediaType(contentType);
            }

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(resource);

        } catch (Exception exception) {
            exception.printStackTrace();

            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }
}