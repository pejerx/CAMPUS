package edu.cit.garol.campus.itemreport;

import jakarta.persistence.*;

@Entity
@Table(name = "item_reports")
public class ItemReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String userId;

    private String reportType;

    private String itemName;

    private String category;

    @Column(length = 1000)
    private String description;

    private String lastSeenLocation;

    private String imagePath;

    private String status = "Pending";

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getReportType() {
        return reportType;
    }

    public String getItemName() {
        return itemName;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return lastSeenLocation;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.lastSeenLocation = location;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}