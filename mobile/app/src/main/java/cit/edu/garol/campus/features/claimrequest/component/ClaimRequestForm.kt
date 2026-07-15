package cit.edu.garol.campus.features.claimrequest.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import android.net.Uri
import cit.edu.garol.campus.features.admin.model.AdminReportItem

private val AdminMaroon = Color(0xFF800000)

@Composable
fun ClaimRequestForm(

    selectedItem: AdminReportItem,

    claimantName: String,
    claimantEmail: String,
    claimantPhone: String,
    itemDescription: String,
    additionalInformation: String,

    onClaimantNameChange: (String) -> Unit,
    onClaimantEmailChange: (String) -> Unit,
    onClaimantPhoneChange: (String) -> Unit,
    onItemDescriptionChange: (String) -> Unit,
    onAdditionalInformationChange: (String) -> Unit,
    selectedImageUri: Uri?,
    onChooseProofImage: () -> Unit,

    onSubmit: () -> Unit

) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 500.dp)
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
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            label = {
                Text("Item Name")
            }
        )

        OutlinedTextField(
            value = selectedItem.reportType,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            label = {
                Text("Report Type")
            }
        )


        OutlinedTextField(
            value = selectedItem.category,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            label = {
                Text("Category")
            }
        )

        OutlinedTextField(
            value = selectedItem.lastSeenLocation ?: "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
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
            onValueChange = onClaimantNameChange,
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            label = {
                Text("Full Name")
            }
        )

        OutlinedTextField(
            value = claimantEmail,
            onValueChange = onClaimantEmailChange,
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            label = {
                Text("Email")
            }
        )

        OutlinedTextField(
            value = claimantPhone,
            onValueChange = onClaimantPhoneChange,
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            label = {
                Text("Phone Number")
            }
        )

        OutlinedTextField(
            value = itemDescription,
            onValueChange = onItemDescriptionChange,
            modifier = Modifier.fillMaxWidth(),
            minLines = 4,
            label = {
                Text("Describe why this item belongs to you")
            }
        )

        OutlinedTextField(
            value = additionalInformation,
            onValueChange = onAdditionalInformationChange,
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            label = {
                Text("Additional Information (Optional)")
            }
        )

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onChooseProofImage
        ) {
            Icon(
                imageVector = Icons.Default.Image,
                contentDescription = "Choose Proof Image"
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