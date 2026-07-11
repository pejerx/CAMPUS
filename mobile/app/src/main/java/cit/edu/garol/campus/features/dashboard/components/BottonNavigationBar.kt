package cit.edu.garol.campus.features.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomNavigationBar(
    onHomeClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onReportClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(Color.White)
            .padding(horizontal = 26.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem(
            label = "Home",
            icon = Icons.Default.Home,
            onClick = onHomeClick
        )

        Box(
            modifier = Modifier
                .size(58.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(Color(0xFF800000))
                .clickable { onReportClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = "Report",
                tint = Color(0xFFFFD700),
                modifier = Modifier.size(32.dp)
            )
        }

        BottomNavItem(
            label = "Profile",
            icon = Icons.Default.Person,
            onClick = onProfileClick
        )
    }
}

@Composable
private fun BottomNavItem(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color(0xFF800000),
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = label,
            fontSize = 10.sp,
            color = Color.Black
        )
    }
}