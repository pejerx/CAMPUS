package cit.edu.garol.campus.features.dashboard.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cit.edu.garol.campus.features.dashboard.components.BottomNavigationBar
import cit.edu.garol.campus.features.dashboard.components.HomeTopBar
import cit.edu.garol.campus.features.dashboard.components.ItemGridCard
import cit.edu.garol.campus.features.dashboard.components.ReportItemDialog
import cit.edu.garol.campus.features.dashboard.components.UserSideBar
import androidx.lifecycle.viewmodel.compose.viewModel
import cit.edu.garol.campus.core.network.RetrofitInstance
import cit.edu.garol.campus.features.admin.repository.AdminRepository
import cit.edu.garol.campus.features.admin.viewmodel.AdminViewModel
import cit.edu.garol.campus.features.admin.viewmodel.AdminViewModelFactory
import cit.edu.garol.campus.features.dashboard.components.ItemDetailsDialog
import cit.edu.garol.campus.features.admin.model.AdminReportItem
import cit.edu.garol.campus.features.claimrequest.component.ClaimRequestDialog
import cit.edu.garol.campus.features.claimrequest.component.ClaimRequestForm
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDashboardPage(
    onLogout: () -> Unit = {}
) {
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()

    var showReportDialog by remember {
        mutableStateOf(false)
    }

    val currentUserId = "26-0000-000"

    val viewModel: AdminViewModel = viewModel(
        factory = AdminViewModelFactory(
            AdminRepository(
                RetrofitInstance.adminApi
            )
        )
    )

    LaunchedEffect(Unit) {
        viewModel.loadPublicReports()
    }

    val uiState by viewModel.dashboardUiState.collectAsState()

    var selectedItem by remember {
        mutableStateOf<AdminReportItem?>(null)
    }

    var showClaimRequestDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(uiState.reportedItems) {
        uiState.reportedItems.forEach {
            println("REPORT -> $it")
        }
    }
    if (showReportDialog) {
        ReportItemDialog(
            userId = currentUserId,
            onDismiss = {
                showReportDialog = false
            },
            onSubmitLostItem = { userId, itemName, category, description, lastSeenLocation, imageUri, imageFileName ->
                println("Lost Item Report")
                println("userId: $userId")
                println("itemName: $itemName")
                println("category: $category")
                println("description: $description")
                println("lastSeenLocation: $lastSeenLocation")
                println("imageUri: $imageUri")
                println("imageFileName: $imageFileName")

                showReportDialog = false
            },
            onSubmitFoundItem = { userId, itemName, category, description, foundLocation, imageUri, imageFileName ->
                println("Found Item Report")
                println("userId: $userId")
                println("itemName: $itemName")
                println("category: $category")
                println("description: $description")
                println("foundLocation: $foundLocation")
                println("imageUri: $imageUri")
                println("imageFileName: $imageFileName")

                showReportDialog = false
            }
        )
    }

    selectedItem?.let { item ->

        ItemDetailsDialog(
            item = item,
            imageBaseUrl = "http://10.0.2.2:8080",

            onDismiss = {
                selectedItem = null
            },

            onClaimClick = {

                showClaimRequestDialog = true

            }

        )

        if (showClaimRequestDialog) {
            ClaimRequestDialog(

                selectedItem = item,
                onDismiss = {
                    showClaimRequestDialog = false
                },
                onSubmit = {
                    showClaimRequestDialog = false
                    selectedItem = null
                }
            )
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.White
            ) {
                UserSideBar(
                    onHomeClick = {
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    onHelpClick = {
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    onAboutClick = {
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    onLogoutClick = {
                        scope.launch {
                            drawerState.close()
                        }

                        onLogout()
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                HomeTopBar(
                    onMenuClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            },
            bottomBar = {
                BottomNavigationBar(
                    onHomeClick = {},
                    onProfileClick = {},
                    onReportClick = {
                        showReportDialog = true
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {},
                    containerColor = Color(0xFF800000),
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
                items(uiState.reportedItems) { item ->
                    ItemGridCard(
                        item = item,
                        onSeeDetails = {
                            selectedItem = it
                        }
                    )
                }

            }
        }
    }
}