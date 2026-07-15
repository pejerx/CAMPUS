package cit.edu.garol.campus.features.claimrequest.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cit.edu.garol.campus.features.admin.model.AdminReportItem

private val AdminMaroon = Color(0xFF800000)
private val AdminGold = Color(0xFFFFD700)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserClaimRequest(

    selectedItem: AdminReportItem,

    onBack: () -> Unit,

    onSubmit: () -> Unit = {}

) {

    var claimantName by remember {
        mutableStateOf("")
    }

    var claimantEmail by remember {
        mutableStateOf("")
    }

    var claimantPhone by remember {
        mutableStateOf("")
    }

    var itemDescription by remember {
        mutableStateOf("")
    }

    var additionalInformation by remember {
        mutableStateOf("")
    }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text(
                        text = "Claim Request",
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

        }

    ) { paddingValues ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp)
                .verticalScroll(
                    rememberScrollState()
                ),

            verticalArrangement = Arrangement.spacedBy(14.dp)

        ) {

            Text(
                text = "Item Information",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = AdminMaroon
            )

            OutlinedTextField(
                value = selectedItem.itemName,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Item Name")
                }
            )

            OutlinedTextField(
                value = selectedItem.reportType,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Report Type")
                }
            )

            OutlinedTextField(
                value = selectedItem.category,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Category")
                }
            )

            OutlinedTextField(
                value = selectedItem.lastSeenLocation ?: "",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Location")
                }
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Claimant Information",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = AdminMaroon
            )

            OutlinedTextField(
                value = claimantName,
                onValueChange = {
                    claimantName = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Full Name")
                }
            )

            OutlinedTextField(
                value = claimantEmail,
                onValueChange = {
                    claimantEmail = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Email")
                }
            )

            OutlinedTextField(
                value = claimantPhone,
                onValueChange = {
                    claimantPhone = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Phone Number")
                }
            )

            OutlinedTextField(
                value = itemDescription,
                onValueChange = {
                    itemDescription = it
                },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4,
                label = {
                    Text("Describe the item and why it belongs to you")
                }
            )

            OutlinedTextField(
                value = additionalInformation,
                onValueChange = {
                    additionalInformation = it
                },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                label = {
                    Text("Additional Information (Optional)")
                }
            )

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    /*
                     * Image picker
                     * will be added next.
                     */
                }
            ) {

                Icon(
                    imageVector = Icons.Default.Image,
                    contentDescription = null
                )
                Spacer(
                    modifier = Modifier.height(4.dp)
                )
                Text("Choose Proof Image")

            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onSubmit,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AdminMaroon,
                    contentColor = Color.White
                )
            ) {

                Text("Submit Claim Request")

            }

        }

    }

}