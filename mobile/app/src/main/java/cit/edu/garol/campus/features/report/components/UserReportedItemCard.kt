package cit.edu.garol.campus.features.report.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cit.edu.garol.campus.features.admin.model.AdminReportItem

@Composable
fun UserReportedItemCard(
    report: AdminReportItem,
    onSeeDetails: () -> Unit
) {

    val imageUrl = report.imagePath?.let {
        "http://10.0.2.2:8080/$it"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSeeDetails()
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            if (imageUrl != null) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = report.itemName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray)
                )
            }

            Text(
                text = report.itemName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )

            Text(
                text = report.category,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                maxLines = 1
            )

            Text(
                text = report.reportType,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF800000),
                fontWeight = FontWeight.SemiBold
            )

            AssistChip(
                onClick = {},
                enabled = false,
                label = {
                    Text(report.status)
                },
                colors = AssistChipDefaults.assistChipColors(
                    disabledContainerColor = statusColor(report.status),
                    disabledLabelColor = Color.White
                )
            )

            Button(
                onClick = onSeeDetails,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF800000)
                )
            ) {
                Text("See Details")
            }
        }
    }
}

@Composable
private fun statusColor(status: String): Color {
    return when (status) {
        "Under Review" -> Color(0xFFFFA000)
        "Unclaimed" -> Color(0xFF2E7D32)
        "Pending Claim" -> Color(0xFF1976D2)
        "Claimed" -> Color(0xFF616161)
        "Rejected" -> Color(0xFFD32F2F)
        else -> MaterialTheme.colorScheme.primary
    }
}