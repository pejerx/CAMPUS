package edu.cit.garol.campus.notification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository
        extends JpaRepository<Notification, Integer> {

    /*
     * Returns all notifications of a user,
     * newest first.
     */
    List<Notification> findByUserIdOrderByCreatedAtDesc(
            String userId
    );

    /*
     * Returns unread notifications of a user.
     */
    List<Notification> findByUserIdAndIsReadFalseOrderByCreatedAtDesc(
            String userId
    );

    /*
     * Counts unread notifications.
     */
    long countByUserIdAndIsReadFalse(
            String userId
    );
}