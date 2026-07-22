package cit.edu.garol.campus.features.claimrequest.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cit.edu.garol.campus.core.network.RetrofitInstance
import cit.edu.garol.campus.features.claimrequest.component.ClaimRequestCard
import cit.edu.garol.campus.features.claimrequest.repository.ClaimRequestRepository
import cit.edu.garol.campus.features.claimrequest.viewmodel.ClaimRequestViewModel
import cit.edu.garol.campus.features.claimrequest.viewmodel.ClaimRequestViewModelFactory

private val AdminMaroon = Color(0xFF800000)
private val AdminGold = Color(0xFFFFD700)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminClaimRequest(
    onBack: () -> Unit
) {

    val viewModel: ClaimRequestViewModel = viewModel(
        factory = ClaimRequestViewModelFactory(
            ClaimRequestRepository(
                RetrofitInstance.claimRequestApi
            )
        )
    )

    val uiState by viewModel.uiState.collectAsState()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {
        viewModel.loadClaimRequests()
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearMessage()
        }
    }

    LaunchedEffect(uiState.successMessage) {
        uiState.successMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearMessage()
        }
    }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text = "Claim Requests",
                        color = Color.White
                    )

                },

                navigationIcon = {

                    IconButton(
                        onClick = onBack
                    ) {

                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = AdminGold
                        )

                    }

                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AdminMaroon
                )

            )

        },

        snackbarHost = {

            SnackbarHost(snackbarHostState)

        },

        containerColor = Color.White

    ) { paddingValues ->

        when {

            uiState.isLoading -> {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {

                    CircularProgressIndicator(
                        color = AdminMaroon
                    )

                }

            }

            uiState.claimRequests.isEmpty() -> {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "No claim requests found.",
                        style = MaterialTheme.typography.bodyLarge
                    )

                }

            }

            else -> {

                LazyColumn(

                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),

                    verticalArrangement = Arrangement.spacedBy(12.dp),

                    contentPadding = PaddingValues(16.dp)

                ) {

                    items(
                        items = uiState.claimRequests,
                        key = { it.id }
                    ) { claimRequest ->

                        ClaimRequestCard(

                            claimRequest = claimRequest,

                            onApprove = {

                                viewModel.approveClaimRequest(claimRequest.id
                                )
                            },
                            onReject = {
                                viewModel.rejectClaimRequest(claimRequest.id
                                )
                            }
                        )
                    }
                }
            }
        }
    }

}