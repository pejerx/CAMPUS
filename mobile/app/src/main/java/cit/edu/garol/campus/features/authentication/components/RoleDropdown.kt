package cit.edu.garol.campus.features.authentication.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun RoleDropdown(
    selectedRole: String,
    onRoleSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    val roles = listOf(
        "Student",
        "Faculty Member",
        "Administrator",
        "Support Staff",
        "Non-Support Staff"
    )

    Box(modifier = modifier) {
        CustomTextField(
            value = selectedRole,
            onValueChange = {},
            placeholder = "Select Role",
            readOnly = true,
            trailingIcon = {
                Text(
                    text = "▼",
                    fontSize = 12.sp,
                    modifier = Modifier.clickable {
                        expanded = true
                    }
                )
            },
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                expanded = true
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            roles.forEach { role ->
                DropdownMenuItem(
                    text = {
                        Text(role)
                    },
                    onClick = {
                        onRoleSelected(role)
                        expanded = false
                    }
                )
            }
        }
    }
}