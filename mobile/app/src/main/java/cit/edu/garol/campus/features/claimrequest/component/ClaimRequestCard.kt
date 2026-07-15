package cit.edu.garol.campus.features.claimrequest.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import cit.edu.garol.campus.features.claimrequest.model.ClaimRequest

private val AdminMaroon = Color(0xFF800000)
private val AdminGold = Color(0xFFFFD700)

@Composable
fun ClaimRequestCard(
    claimRequest: ClaimRequest,
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

            /*
             * Item Information
             */

            Text(
                text = claimRequest.itemReport.itemName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = AdminMaroon
            )

            Text(
                text = "Report Type: ${claimRequest.itemReport.reportType}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Category: ${claimRequest.itemReport.category}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Location: ${
                    claimRequest.itemReport.lastSeenLocation
                        ?: "Not specified"
                }",
                style = MaterialTheme.typography.bodyMedium
            )

            /*
             * Claimant Information
             */

            Text(
                text = "Claimant ID: ${claimRequest.claimantId}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "Name: ${claimRequest.claimantName}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Email: ${claimRequest.claimantEmail}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Phone: ${claimRequest.claimantPhone}",
                style = MaterialTheme.typography.bodyMedium
            )

            /*
             * Description
             */

            Text(
                text = "Item Description",
                fontWeight = FontWeight.Bold,
                color = AdminMaroon
            )

            Text(
                text = claimRequest.itemDescription,
                style = MaterialTheme.typography.bodyMedium
            )

            if (!claimRequest.additionalInformation.isNullOrBlank()) {

                Text(
                    text = "Additional Information",
                    fontWeight = FontWeight.Bold,
                    color = AdminMaroon
                )

                Text(
                    text = claimRequest.additionalInformation,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            /*
             * Status
             */

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Status",
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = claimRequest.status,
                    fontWeight = FontWeight.Bold,
                    color = when (claimRequest.status) {
                        "Pending" -> AdminGold
                        "Approved" -> Color(0xFF2E7D32)
                        "Rejected" -> Color.Red
                        else -> Color.Black
                    }
                )
            }

            Text(
                text = "Submitted: ${claimRequest.createdAt}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            /*
             * Proof Image
             *
             * We'll display the actual proof image later
             * using Coil.
             */

            if (!claimRequest.proofImagePath.isNullOrBlank()) {

                Text(
                    text = "Proof Image Available",
                    color = AdminMaroon,
                    fontWeight = FontWeight.SemiBold
                )
            }

            /*
             * Buttons
             */

            if (claimRequest.status.equals("Pending", true)) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AdminMaroon,
                            contentColor = Color.White
                        ),
                        onClick = onApprove
                    ) {

                        Text("Approve")

                    }

                    OutlinedButton(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
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
}