package cit.edu.garol.campus.features.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserSideBar(
    onHomeClick: () -> Unit,
    onHelpClick: () -> Unit,
    onAboutClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(245.dp)
            .background(Color.White)
            .padding(horizontal = 28.dp, vertical = 24.dp)
    ) {
        Text(
            text = "Menu",
            fontSize = 12.sp,
            color = Color.LightGray
        )

        Spacer(modifier = Modifier.height(12.dp))

        Divider(color = Color(0xFFE0E0E0))

        Spacer(modifier = Modifier.height(70.dp))

        Text(
            text = "RetrieveIT",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(55.dp))

        DrawerMenuItem(
            text = "Home",
            onClick = onHomeClick
        )

        DrawerMenuItem(
            text = "Help & Support",
            onClick = onHelpClick
        )

        DrawerMenuItem(
            text = "About us",
            onClick = onAboutClick
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Sign Out",
            fontSize = 12.sp,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {
                    onLogoutClick()
                }
        )

        Spacer(modifier = Modifier.height(80.dp))

        Divider(color = Color(0xFFE0E0E0))
    }
}

@Composable
private fun DrawerMenuItem(
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Default.ArrowForwardIos,
            contentDescription = text,
            tint = Color.Black,
            modifier = Modifier.size(14.dp)
        )
    }
}

