package edu.cit.garol.campus.notification;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:5173")
public class NotificationController {

    private final NotificationRepository notificationRepository;

    public NotificationController(
            NotificationRepository notificationRepository
    ) {
        this.notificationRepository = notificationRepository;
    }

    /*
     * Get all notifications of a user.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getUserNotifications(
            @PathVariable String userId
    ) {

        return ResponseEntity.ok(
                notificationRepository
                        .findByUserIdOrderByCreatedAtDesc(userId)
        );

    }

    /*
     * Get unread notification count.
     */
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Long> getUnreadCount(
            @PathVariable String userId
    ) {

        return ResponseEntity.ok(
                notificationRepository
                        .countByUserIdAndIsReadFalse(userId)
        );

    }

    /*
     * Mark a notification as read.
     */
    @PutMapping("/{id}/read")
    public ResponseEntity<?> markAsRead(
            @PathVariable Integer id
    ) {

        Notification notification =
                notificationRepository
                        .findById(id)
                        .orElse(null);

        if (notification == null) {

            return ResponseEntity
                    .badRequest()
                    .body("Notification not found.");

        }

        notification.setRead(true);

        notificationRepository.save(notification);

        return ResponseEntity.ok(notification);

    }

    /*
     * Mark all notifications of a user as read.
     */
    @PutMapping("/user/{userId}/read-all")
    public ResponseEntity<?> markAllAsRead(
            @PathVariable String userId
    ) {

        List<Notification> notifications =
                notificationRepository
                        .findByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId);

        for (Notification notification : notifications) {

            notification.setRead(true);

        }

        notificationRepository.saveAll(notifications);

        return ResponseEntity.ok().build();

    }

}