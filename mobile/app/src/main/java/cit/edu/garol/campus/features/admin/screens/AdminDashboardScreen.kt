package cit.edu.garol.campus.features.admin.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cit.edu.garol.campus.ui.theme.Background
import cit.edu.garol.campus.ui.theme.Black

private val AdminMaroon = Color(0xFF7A0019)
private val AdminGold = Color(0xFFFFCC33)

@Composable
fun AdminDashboardScreen(
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(AdminMaroon)
                .padding(
                    horizontal = 24.dp,
                    vertical = 26.dp
                )
        ) {
            Text(
                text = "CAMPUS",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = "Administrator Dashboard",
                fontSize = 14.sp,
                color = AdminGold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(22.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "Dashboard Overview",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Black
            )

            AdminDashboardOption(
                title = "Reported Items",
                description = "Review lost and found item reports."
            )

            AdminDashboardOption(
                title = "Claim Requests",
                description = "Review requests submitted by users."
            )

            AdminDashboardOption(
                title = "Report Status",
                description = "Approve, reject, or update item statuses."
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AdminMaroon,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "LOG OUT",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun AdminDashboardOption(
    title: String,
    description: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = AdminMaroon
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = description,
                fontSize = 13.sp,
                color = Color.DarkGray
            )
        }
    }
}