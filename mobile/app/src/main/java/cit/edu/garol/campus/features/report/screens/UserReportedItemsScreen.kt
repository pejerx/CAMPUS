package cit.edu.garol.campus.features.report.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import cit.edu.garol.campus.authentication.UserSession
import cit.edu.garol.campus.features.admin.model.AdminReportItem
import cit.edu.garol.campus.features.report.repository.ReportRepository
import cit.edu.garol.campus.features.report.viewmodel.ReportViewModel
import cit.edu.garol.campus.features.report.viewmodel.ReportViewModelFactory
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import cit.edu.garol.campus.features.dashboard.components.HomeTopBar
import cit.edu.garol.campus.features.dashboard.components.UserSideBar
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
@Composable
fun UserReportedItemsScreen(
    onHomeClick: () -> Unit,
    onClaimRequestsClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onSeeDetails: (AdminReportItem) -> Unit
) {
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()

    val reportViewModel: ReportViewModel = viewModel(
        factory = ReportViewModelFactory(
            ReportRepository()
        )
    )

    val uiState by reportViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        UserSession.currentUser?.let { user ->
            reportViewModel.loadMyReports(user.id)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {

            ModalDrawerSheet {

                UserSideBar(

                    onHomeClick = {
                        scope.launch {
                            drawerState.close()
                        }
                        onHomeClick()
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
                        onClaimRequestsClick()
                    },

                    onLogoutClick = {
                        scope.launch {
                            drawerState.close()
                        }
                        onLogoutClick()
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

            }

        ) { padding ->

            androidx.compose.foundation.layout.Box(
                modifier = Modifier.padding(padding)
            ) {

                UserReportedItems(
                    reports = uiState.reports,
                    isLoading = uiState.isLoading,
                    errorMessage = uiState.errorMessage,
                    onSeeDetails = onSeeDetails
                )

            }

        }

    }
}