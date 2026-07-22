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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.layout.ContentScale
import cit.edu.garol.campus.features.admin.model.AdminReportItem
import coil.compose.AsyncImage
import cit.edu.garol.campus.core.ApiConfig

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

        if (!item.imagePath.isNullOrBlank()) {
            AsyncImage(
                model = "${ApiConfig.BASE_URL}${item.imagePath}",
                contentDescription = item.itemName,
                modifier = Modifier.size(95.dp),
                contentScale = ContentScale.Crop
            )
        } else {
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

        Button(
            onClick = {
                onSeeDetails(item)
            },
            modifier = Modifier
                .fillMaxWidth(0.65f)   // 65% of the card width
                .height(36.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Maroon,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "See Details",
                fontSize = 12.sp
            )

        }

    }
}