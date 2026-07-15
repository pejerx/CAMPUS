package cit.edu.garol.campus.features.admin.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cit.edu.garol.campus.features.admin.model.AdminReportItem

@Composable
fun AdminDashboardCard(
    report: AdminReportItem,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = report.itemName.ifBlank {
                    "Unnamed item"
                },
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Type: ${report.reportType.ifBlank { "Not specified" }}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Category: ${report.category.ifBlank { "Not specified" }}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Location: ${
                    report.lastSeenLocation?.takeIf { it.isNotBlank() } ?: "Not specified"
                }",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Status: ${report.status.ifBlank { "Under Review" }}",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}