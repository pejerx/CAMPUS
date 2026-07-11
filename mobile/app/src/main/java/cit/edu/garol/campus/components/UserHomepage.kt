package cit.edu.garol.campus.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cit.edu.garol.campus.components.BottomNavigationBar
import cit.edu.garol.campus.components.HomeTopBar
import cit.edu.garol.campus.components.ItemGridCard
import cit.edu.garol.campus.model.ItemCardData

@Composable
fun UserHomePage() {
    val items = listOf(
        ItemCardData("4/13/2026", "UNCLAIMED", "FOUND ITEM", "Wallet"),
        ItemCardData("4/13/2026", "UNFOUND", "LOST ITEM", "Phone"),
        ItemCardData("4/13/2026", "FOUND", "LOST ITEM", "Watch"),
        ItemCardData("4/13/2026", "CLAIMED", "FOUND ITEM", "Laptop"),
        ItemCardData("4/13/2026", "FOUND", "FOUND ITEM", "ID"),
        ItemCardData("4/13/2026", "UNFOUND", "LOST ITEM", "Bag")
    )

    Scaffold(
        topBar = {
            HomeTopBar()
        },
        bottomBar = {
            BottomNavigationBar()
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // TODO: Notifications page
                },
                containerColor = Color(0xFF3F4229),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications"
                )
            }
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            items(items) { item ->
                ItemGridCard(
                    item = item,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}