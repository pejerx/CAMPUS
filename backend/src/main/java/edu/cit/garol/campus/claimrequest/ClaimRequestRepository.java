package edu.cit.garol.campus.claimrequest;

import edu.cit.garol.campus.itemreport.ItemReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClaimRequestRepository
        extends JpaRepository<ClaimRequest, Integer> {

    /*
     * Retrieve all claim requests for a specific item.
     */
    List<ClaimRequest> findByItemReport(ItemReport itemReport);

    /*
     * Retrieve all claim requests submitted by a user.
     */
    List<ClaimRequest> findByClaimantId(String claimantId);

    /*
     * Retrieve all claim requests with a specific status.
     */
    List<ClaimRequest> findByStatus(String status);

    /*
     * Retrieve all claim requests for an item with a specific status.
     */
    List<ClaimRequest> findByItemReportAndStatus(
            ItemReport itemReport,
            String status
    );

    /*
     * Check if a user has already submitted a claim
     * for a particular item.
     */
    boolean existsByItemReportAndClaimantId(
            ItemReport itemReport,
            String claimantId
    );

}