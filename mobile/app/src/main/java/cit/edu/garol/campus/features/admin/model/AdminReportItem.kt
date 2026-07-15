package cit.edu.garol.campus.features.admin.model

data class AdminReportItem(
    val id: Int,
    val userId: String? = null,
    val reportType: String,
    val itemName: String,
    val category: String,
    val description: String,
    val lastSeenLocation: String? = null,
    val imagePath: String? = null,
    val createdAt: String? = null,
    val status: String
)