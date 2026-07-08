package cit.edu.garol.campus.features.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cit.edu.garol.campus.features.dashboard.model.ItemCardData

@Composable
fun ItemGridCard(
    item: ItemCardData,
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
            Text(text = item.date, fontSize = 10.sp)
            Text(
                text = "Status: ${item.status}",
                fontSize = 10.sp,
                color = when (item.status) {
                    "FOUND", "CLAIMED" -> Color(0xFF008000)
                    else -> Color.Red
                }
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        Box(
            modifier = Modifier
                .size(95.dp)
                .background(Color(0xFFE8E8E8), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = item.itemName.take(1),
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = item.itemType,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "See Details",
            color = Color.Black,
            fontSize = 12.sp
        )
    }

    Divider(color = Color(0xFFE0E0E0))
}