package cit.edu.garol.campus.features.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cit.edu.garol.campus.features.admin.model.AdminReportItem

private val Maroon = Color(0xFF800000)
@Composable
fun ItemGridCard(
    item: AdminReportItem,
    onSeeDetails: (AdminReportItem) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .height(240.dp)
            .background(Color.White)
            .padding(10.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = item.createdAt ?: "Unknown Date",
                fontSize = 10.sp
            )

            Text(
                text = item.status,
                fontSize = 10.sp,
                color = when (item.status) {

                    "Claimed" ->
                        Color(0xFF008000)

                    "Pending Claim" ->
                        Color(0xFFFF9800)

                    else ->
                        Color.Red
                }
            )
        }

        Spacer(
            modifier = Modifier.height(28.dp)
        )

        /*
         * Item image.
         *
         * We'll replace this with the actual image
         * after we add Coil.
         */

        Box(
            modifier = Modifier
                .size(95.dp)
                .background(
                    Color(0xFFE8E8E8),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = item.itemName.take(1),
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )

        }

        Spacer(
            modifier = Modifier.height(22.dp)
        )

        Text(
            text = item.reportType,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        Text(
            text = item.itemName,
            fontSize = 13.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        TextButton(
            onClick = {
                onSeeDetails(item)
            }
        ) {
            Text(
                text = "See Details",
                color = Maroon
            )
        }
    }

    Divider(
        color = Color(0xFFE0E0E0)
    )
}