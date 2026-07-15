package cit.edu.garol.campus.features.admin.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cit.edu.garol.campus.features.admin.model.AdminReportItem

private val AdminMaroon = Color(0xFF800000)
private val AdminGold = Color(0xFFFFD700)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminReportCard(
    report: AdminReportItem,
    onStatusChanged: (String) -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedStatus by remember(report.status) {
        mutableStateOf(report.status)
    }

    val statuses = listOf(
        "Unclaimed",
        "Pending Claim",
        "Claimed"
    )

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
                text = "Location: ${report.lastSeenLocation ?: "Not specified"}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )

            Text(
                text = report.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )

            Text(
                text = "Reported By: ${report.userId ?: "Unknown"}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )

            Text(
                text = "Date Reported: ${report.createdAt}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {

                OutlinedTextField(
                    value = selectedStatus,
                    onValueChange = {},
                    readOnly = true,
                    label = {
                        Text("Status")
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {

                    statuses.forEach { status ->

                        DropdownMenuItem(
                            text = {
                                Text(status)
                            },
                            onClick = {

                                selectedStatus = status
                                expanded = false

                                onStatusChanged(status)
                            }
                        )
                    }
                }
            }
        }
    }
}