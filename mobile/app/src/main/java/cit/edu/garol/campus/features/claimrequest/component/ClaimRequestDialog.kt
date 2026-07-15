package cit.edu.garol.campus.features.claimrequest.component

import android.content.Context
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import cit.edu.garol.campus.authentication.UserSession
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import cit.edu.garol.campus.core.network.RetrofitInstance
import cit.edu.garol.campus.features.admin.model.AdminReportItem
import cit.edu.garol.campus.features.claimrequest.repository.ClaimRequestRepository
import cit.edu.garol.campus.features.claimrequest.viewmodel.ClaimRequestViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

@Composable
fun ClaimRequestDialog(
    selectedItem: AdminReportItem,
    onDismiss: () -> Unit,
    onSubmit: () -> Unit
) {

    val currentUser = UserSession.currentUser
    val context = LocalContext.current
    val viewModel = remember {
        ClaimRequestViewModel(
            ClaimRequestRepository(
                RetrofitInstance.claimRequestApi
            )
        )
    }

    var claimantName by remember {
        mutableStateOf(
            currentUser?.let {
                "${it.firstName} ${it.lastName}"
            } ?: ""
        )
    }

    var claimantEmail by remember {
        mutableStateOf(
            currentUser?.email ?: ""
        )
    }

    var claimantPhone by remember {
        mutableStateOf(
            currentUser?.contactNumber ?: ""
        )
    }
    var itemDescription by remember { mutableStateOf("") }
    var additionalInformation by remember {
        mutableStateOf("")
    }

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->

            selectedImageUri = uri

        }

    AlertDialog(

        onDismissRequest = onDismiss,
        confirmButton = {},
        dismissButton = {},
        title = {

            Text("Claim Request")

        },

        text = {

            ClaimRequestForm(

                selectedItem = selectedItem,
                selectedImageUri = selectedImageUri,
                claimantName = claimantName,
                claimantEmail = claimantEmail,
                claimantPhone = claimantPhone,
                itemDescription = itemDescription,
                additionalInformation = additionalInformation,

                onClaimantNameChange = {
                    claimantName = it
                },
                onClaimantEmailChange = {
                    claimantEmail = it
                },
                onClaimantPhoneChange = {
                    claimantPhone = it
                },
                onItemDescriptionChange = {
                    itemDescription = it
                },
                onAdditionalInformationChange = {
                    additionalInformation = it
                },
                onChooseProofImage = {
                    imagePickerLauncher.launch("image/*")
                },

                onSubmit = {

                    val user = UserSession.currentUser ?: return@ClaimRequestForm

                    viewModel.submitClaimRequest(
                        itemReportId = selectedItem.id,
                        claimantId = user.id,
                        claimantName = "${user.firstName} ${user.lastName}",
                        claimantEmail = user.email,
                        claimantPhone = user.contactNumber,
                        itemDescription = itemDescription,
                        additionalInformation = additionalInformation,
                        proofImage = selectedImageUri?.let {
                            createMultipartFromUri(
                                context,
                                it
                            )
                        }
                    )
                    onSubmit()
                }
            )
        }
    )
}

private fun createMultipartFromUri(
    context: Context,
    uri: Uri
): MultipartBody.Part {

    val inputStream =
        context.contentResolver.openInputStream(uri)!!

    val file = File(
        context.cacheDir,
        "proof_image.jpg"
    )

    FileOutputStream(file).use { output ->

        inputStream.copyTo(output)

    }

    val requestBody =
        file.asRequestBody(
            "image/*".toMediaTypeOrNull()
        )

    return MultipartBody.Part.createFormData(

        "proofImage",
        file.name,
        requestBody
    )
}