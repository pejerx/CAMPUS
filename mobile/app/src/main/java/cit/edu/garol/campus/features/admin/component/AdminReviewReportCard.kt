package cit.edu.garol.campus.features.admin.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cit.edu.garol.campus.features.admin.model.AdminReportItem

private val AdminMaroon = Color(0xFF800000)
private val AdminGold = Color(0xFFFFD700)

@Composable
fun AdminReviewReportCard(
    report: AdminReportItem,
    onApprove: () -> Unit,
    onReject: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Text(
                text = report.itemName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = AdminMaroon
            )

            Text(
                text = "Reported By: ${report.userId ?: "Unknown"}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )

            Text(
                text = "Report Type: ${report.reportType}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )

            Text(
                text = "Category: ${report.category}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )

            Text(
                text = "Location: ${
                    report.lastSeenLocation ?: "Not specified"
                }",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )

            Text(
                text = report.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Status",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Text(
                    text = report.status,
                    fontWeight = FontWeight.Bold,
                    color = AdminGold
                )
            }

            Text(
                text = "Date Reported: ${report.createdAt}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Button(
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AdminMaroon,
                        contentColor = Color.White
                    ),
                    onClick = onApprove
                ) {
                    Text("Approve")
                }

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = onReject
                ) {
                    Text(
                        text = "Reject",
                        color = AdminMaroon
                    )
                }
            }
        }
    }
}