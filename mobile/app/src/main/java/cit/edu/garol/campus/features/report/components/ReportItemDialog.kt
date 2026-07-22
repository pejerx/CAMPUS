package cit.edu.garol.campus.features.dashboard.components

import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportItemDialog(
    userId: String,
    onDismiss: () -> Unit,
    onSubmitLostItem: (
        userId: String,
        itemName: String,
        category: String,
        description: String,
        lastSeenLocation: String,
        imageUri: Uri?,
        imageFileName: String
    ) -> Unit,
    onSubmitFoundItem: (
        userId: String,
        itemName: String,
        category: String,
        description: String,
        foundLocation: String,
        imageUri: Uri?,
        imageFileName: String
    ) -> Unit
) {
    val context = LocalContext.current

    var selectedReportType by remember { mutableStateOf("LOST") }

    var itemName by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    val categories = listOf(
        "ID Card",
        "Wallet",
        "Keys",
        "Phone",
        "Laptop",
        "Bag",
        "Notebook",
        "Umbrella",
        "Water Bottle",
        "Clothing",
        "Jewelry",
        "Others"
    )

    var expanded by remember {
        mutableStateOf(false)
    }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageFileName by remember { mutableStateOf("") }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
        imageFileName = uri?.let {
            getFileNameFromUri(context, it)
        } ?: ""
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        dismissButton = {},
        containerColor = Color.White,
        shape = RoundedCornerShape(18.dp),
        title = {
            Text(
                text = "Report Item",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF800000)
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 560.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ReportTypeButton(
                        text = "Lost Item",
                        selected = selectedReportType == "LOST",
                        onClick = {
                            selectedReportType = "LOST"
                            location = ""
                        },
                        modifier = Modifier.weight(1f)
                    )

                    ReportTypeButton(
                        text = "Found Item",
                        selected = selectedReportType == "FOUND",
                        onClick = {
                            selectedReportType = "FOUND"
                            location = ""
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                ReportTextField(
                    value = itemName,
                    onValueChange = { itemName = it },
                    label = "Item Name"
                )

                Spacer(modifier = Modifier.height(10.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {

                    OutlinedTextField(
                        value = category,
                        onValueChange = {},
                        readOnly = true,
                        label = {
                            Text("Category")
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF800000),
                            unfocusedBorderColor = Color(0xFFD4D4D4),
                            cursorColor = Color(0xFF800000)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ) {

                        categories.forEach { option ->

                            DropdownMenuItem(
                                text = {
                                    Text(option)
                                },
                                onClick = {
                                    category = option
                                    expanded = false
                                }

                            )

                        }

                    }

                }

                Spacer(modifier = Modifier.height(10.dp))

                Spacer(modifier = Modifier.height(10.dp))

                ReportTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = "Description",
                    minLines = 3
                )

                Spacer(modifier = Modifier.height(10.dp))

                ReportTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = if (selectedReportType == "LOST") {
                        "Last Seen Location"
                    } else {
                        "Found Location"
                    }
                )

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = {
                        imagePickerLauncher.launch("image/*")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFD700),
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "UPLOAD IMAGE",
                        fontWeight = FontWeight.Bold
                    )
                }

                if (imageFileName.isNotBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Selected file: $imageFileName",
                        fontSize = 11.sp,
                        color = Color.DarkGray
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        if (selectedReportType == "LOST") {
                            onSubmitLostItem(
                                userId,
                                itemName,
                                category,
                                description,
                                location,
                                imageUri,
                                imageFileName
                            )
                        } else {
                            onSubmitFoundItem(
                                userId,
                                itemName,
                                category,
                                description,
                                location,
                                imageUri,
                                imageFileName
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF800000),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = if (selectedReportType == "LOST") {
                            "SUBMIT LOST ITEM"
                        } else {
                            "SUBMIT FOUND ITEM"
                        },
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Cancel",
                        color = Color.Black
                    )
                }
            }
        }
    )
}

@Composable
private fun ReportTypeButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(42.dp)
            .background(
                color = if (selected) Color(0xFF800000) else Color(0xFFFFF8DC),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() },
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected) Color.White else Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun ReportTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    minLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(label)
        },
        minLines = minLines,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF800000),
            unfocusedBorderColor = Color(0xFFD4D4D4),
            cursorColor = Color(0xFF800000)
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

private fun getFileNameFromUri(
    context: android.content.Context,
    uri: Uri
): String {
    var fileName = "selected_image"

    val cursor = context.contentResolver.query(
        uri,
        null,
        null,
        null,
        null
    )

    cursor?.use {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)

        if (it.moveToFirst() && nameIndex >= 0) {
            fileName = it.getString(nameIndex)
        }
    }

    return fileName
}