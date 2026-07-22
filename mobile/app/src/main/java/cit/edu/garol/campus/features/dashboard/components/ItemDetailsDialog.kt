package cit.edu.garol.campus.features.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cit.edu.garol.campus.core.ApiConfig
import cit.edu.garol.campus.features.admin.model.AdminReportItem
import coil.compose.AsyncImage

private val Maroon = Color(0xFF800000)

@Composable
fun ItemDetailsDialog(
    item: AdminReportItem,
    onDismiss: () -> Unit,
    onClaimClick: (AdminReportItem) -> Unit = {},
    showClaimButton: Boolean = true,
    currentUserId: String? = null

) {

    AlertDialog(
        onDismissRequest = onDismiss,

        title = {
            Text(
                text = item.itemName,
                style = MaterialTheme.typography.titleLarge,
                color = Maroon,
                fontWeight = FontWeight.Bold
            )
        },

        text = {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                AsyncImage(
                    model = if (!item.imagePath.isNullOrBlank())
                        "${ApiConfig.BASE_URL.removeSuffix("/")}/${item.imagePath}"
                    else
                        null,
                    contentDescription = item.itemName,
                    modifier = Modifier.size(220.dp)
                )

                Text(
                    text = "Status: ${item.status}",
                    color = Maroon,
                    fontWeight = FontWeight.Bold
                )

                Text("Report Type: ${item.reportType}")

                Text("Category: ${item.category}")

                Text(
                    text = "Location: ${
                        item.lastSeenLocation ?: "Not specified"
                    }"
                )

                Text(item.description)

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                item.createdAt?.let {
                    Text("Reported: $it")
                }
            }
        },

        confirmButton = {

            if (
                showClaimButton &&
                item.reportType.equals("FOUND", ignoreCase = true) &&
                item.status == "Unclaimed" &&
                item.userId != currentUserId
            ) {

                Button(
                    onClick = {
                        onClaimClick(item)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Maroon,
                        contentColor = Color.White
                    )
                ) {

                    Text("Claim Item")

                }

            }

        },

        dismissButton = {

            OutlinedButton(
                onClick = onDismiss
            ) {

                Text(
                    text = "Close",
                    color = Maroon
                )

            }

        }

    )

}