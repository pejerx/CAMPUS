package cit.edu.garol.campus.features.admin.model

data class AdminReportItem(
    val id: String = "",
    val userId: String = "",
    val itemName: String = "",
    val category: String = "",
    val description: String = "",
    val lastSeenLocation: String = "",
    val imagePath: String? = null,
    val status: String = "",
    val reportType: String = ""
)