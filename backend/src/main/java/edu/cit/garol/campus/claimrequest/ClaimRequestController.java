package edu.cit.garol.campus.claimrequest;

import edu.cit.garol.campus.itemreport.ItemReport;
import edu.cit.garol.campus.itemreport.ItemReportRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import edu.cit.garol.campus.notification.Notification;
import edu.cit.garol.campus.notification.NotificationRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/claim-requests")
public class ClaimRequestController {

    private final ClaimRequestRepository claimRequestRepository;
    private final ItemReportRepository itemReportRepository;
    private final NotificationRepository notificationRepository;

    public ClaimRequestController(
            ClaimRequestRepository claimRequestRepository,
            ItemReportRepository itemReportRepository,
            NotificationRepository notificationRepository
            
    ) {
        this.claimRequestRepository = claimRequestRepository;
        this.itemReportRepository = itemReportRepository;
        this.notificationRepository = notificationRepository;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> submitClaimRequest(

            @RequestParam Integer itemReportId,
            @RequestParam String claimantId,
            @RequestParam String claimantName,
            @RequestParam String claimantEmail,
            @RequestParam String claimantPhone,
            @RequestParam String itemDescription,
            @RequestParam(required = false)
            String additionalInformation,
            @RequestParam(required = false)
            MultipartFile proofImage

    ) {

        try {

            ItemReport report =
                    itemReportRepository
                            .findById(itemReportId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Item report not found."
                                    ));
            if (claimRequestRepository.existsByItemReportAndClaimantId(
                    report,
                    claimantId
            )) {

                return ResponseEntity
                        .badRequest()
                        .body("You have already submitted a claim for this item.");

            }

            String proofImagePath = null;

            if (proofImage != null && !proofImage.isEmpty()) {

                String uploadDirectory =
                        "uploads/claim-proofs/";

                Files.createDirectories(
                        Paths.get(uploadDirectory)
                );

                String originalFileName =
                        proofImage.getOriginalFilename();

                if (originalFileName == null ||
                        originalFileName.isBlank()) {

                    originalFileName = "proof-image";

                }

                String fileName =
                        UUID.randomUUID() +
                                "_" +
                                originalFileName;

                Path filePath =
                        Paths.get(uploadDirectory + fileName);

                Files.write(
                        filePath,
                        proofImage.getBytes()
                );

                proofImagePath =
                        uploadDirectory + fileName;

            }

            ClaimRequest claimRequest =
                    new ClaimRequest();

            claimRequest.setItemReport(report);
            claimRequest.setClaimantId(claimantId);
            claimRequest.setClaimantName(claimantName);
            claimRequest.setClaimantEmail(claimantEmail);
            claimRequest.setClaimantPhone(claimantPhone);
            claimRequest.setItemDescription(itemDescription);
            claimRequest.setAdditionalInformation(
                    additionalInformation
            );

            claimRequest.setProofImagePath(
                    proofImagePath
            );

            claimRequest.setStatus("Pending");

            claimRequest.setCreatedAt(
                    LocalDateTime.now()
            );

            ClaimRequest savedClaim =
                    claimRequestRepository.save(
                            claimRequest
                    );

            report.setStatus("Pending Claim");

            itemReportRepository.save(report);

            return ResponseEntity.ok(savedClaim);

        } catch (Exception exception) {

            exception.printStackTrace();

            return ResponseEntity
                    .internalServerError()
                    .body("Failed to submit claim request.");

        }

    }

    @GetMapping
    public ResponseEntity<List<ClaimRequest>>
    getAllClaimRequests() {

        return ResponseEntity.ok(
                claimRequestRepository.findAll()
        );

    }

    @GetMapping("/user/{claimantId}")
    public ResponseEntity<List<ClaimRequest>>
    getUserClaimRequests(
            @PathVariable String claimantId
    ) {

        return ResponseEntity.ok(

                claimRequestRepository
                        .findByClaimantId(claimantId)

        );

    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateClaimStatus(

            @PathVariable Integer id,

            @RequestParam String status

    ) {

        try {

            ClaimRequest claimRequest =
                    claimRequestRepository
                            .findById(id)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Claim request not found."
                                    ));

            claimRequest.setStatus(status);

            claimRequestRepository.save(
                    claimRequest
            );

            Notification notification = new Notification();

                notification.setUserId(
                        claimRequest.getClaimantId()
                );

                if ("Approved".equalsIgnoreCase(status)) {

                notification.setTitle(
                        "Claim Request Approved"
                );

                notification.setMessage(
                        "Your claim request has been approved. "
                        + "Please proceed to the Lost and Found Office "
                        + "for identity verification and item release. "
                        + "Kindly bring your school ID and any supporting "
                        + "documents that may help verify ownership."
                );

                } else if ("Rejected".equalsIgnoreCase(status)) {

                notification.setTitle(
                        "Claim Request Rejected"
                );

                notification.setMessage(
                        "Your claim request has been rejected. "
                        + "The submitted proof of ownership "
                        + "was insufficient to verify ownership. "
                        + "If you believe this decision was made in error, "
                        + "please visit the Lost and Found Office "
                        + "for further assistance."
                );

                }

                notification.setRead(false);

                notification.setCreatedAt(
                        LocalDateTime.now()
                );

                notificationRepository.save(
                        notification
                );

            if ("Approved".equalsIgnoreCase(status)) {

                ItemReport report =
                        claimRequest.getItemReport();

                report.setStatus("Claimed");

                itemReportRepository.save(report);

            }

            return ResponseEntity.ok(
                "Claim request updated successfully."
        );

        } catch (Exception exception) {

            exception.printStackTrace();

            return ResponseEntity
                    .internalServerError()
                    .body("Failed to update claim request.");

        }

    }

    @PutMapping("/{id}/approve")
        public ResponseEntity<?> approveClaimRequest(
                @PathVariable Integer id
        ) {

            return updateClaimStatus(id, "Approved");

        }

    @PutMapping("/{id}/reject")
        public ResponseEntity<?> rejectClaimRequest(
                @PathVariable Integer id
        ) {

            return updateClaimStatus(id, "Rejected");

        }


    @GetMapping("/proofs/{fileName:.+}")
    public ResponseEntity<Resource>
    getProofImage(
            @PathVariable String fileName
    ) {

        try {

            Path imagePath =
                    Paths.get("uploads/claim-proofs")
                            .resolve(fileName)
                            .toAbsolutePath()
                            .normalize();

            Resource resource =
                    new UrlResource(
                            imagePath.toUri()
                    );

            if (!resource.exists() ||
                    !resource.isReadable()) {

                return ResponseEntity
                        .notFound()
                        .build();

            }

            String contentType =
                    Files.probeContentType(imagePath);

            MediaType mediaType =
                    MediaType.APPLICATION_OCTET_STREAM;

            if (contentType != null) {

                mediaType =
                        MediaType.parseMediaType(
                                contentType
                        );

            }

            return ResponseEntity
                    .ok()
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