package edu.cit.garol.campus.itemreport;

import edu.cit.garol.campus.itemreport.ItemReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemReportRepository extends JpaRepository<ItemReport, String> {
}