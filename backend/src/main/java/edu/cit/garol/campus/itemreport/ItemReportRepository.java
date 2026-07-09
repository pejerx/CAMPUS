package edu.cit.garol.campus.itemreport;

import edu.cit.garol.campus.itemreport.ItemReport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemReportRepository extends JpaRepository<ItemReport, String> {
    List<ItemReport> findByStatusIn(List<String> statuses);
}