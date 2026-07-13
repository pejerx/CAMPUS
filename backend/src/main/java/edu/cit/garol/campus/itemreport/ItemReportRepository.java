package edu.cit.garol.campus.itemreport;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemReportRepository
        extends JpaRepository<ItemReport, Integer> {

    List<ItemReport> findByStatusIn(List<String> statuses);
    List<ItemReport> findByUserId(String userId);
}