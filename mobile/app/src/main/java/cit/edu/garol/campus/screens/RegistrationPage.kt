package cit.edu.garol.campus.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cit.edu.garol.campus.components.CustomTextField
import cit.edu.garol.campus.components.LinkText
import cit.edu.garol.campus.components.PrimaryButton
import cit.edu.garol.campus.components.RoleDropdown
import cit.edu.garol.campus.components.TopCurvedHeader
import cit.edu.garol.campus.ui.theme.Background
import cit.edu.garol.campus.ui.theme.Black
import cit.edu.garol.campus.ui.theme.TextSecondary
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import cit.edu.garol.campus.model.RegisterRequest
import cit.edu.garol.campus.network.RetrofitInstance
@Composable
fun RegisterPage(
    onGoToLogin: () -> Unit
) {

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TopCurvedHeader()

        Spacer(modifier = Modifier.height(18.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp)
        ) {

            Text(
                text = buildAnnotatedString {

                    append("Create your ")

                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic
                        )
                    ) {
                        append("CAMPUS")
                    }

                    append(" account")
                },
                fontSize = 22.sp,
                color = Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Register to report, search, and claim lost and found items inside the campus.",
                fontSize = 12.sp,
                color = TextSecondary,
                lineHeight = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp)
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                CustomTextField(
                    value = firstName,
                    onValueChange = {
                        firstName = it
                    },
                    placeholder = "First Name",
                    modifier = Modifier.weight(1f)
                )

                CustomTextField(
                    value = lastName,
                    onValueChange = {
                        lastName = it
                    },
                    placeholder = "Last Name",
                    modifier = Modifier.weight(1f)
                )

            }

            Spacer(modifier = Modifier.height(12.dp))

            RoleDropdown(
                selectedRole = selectedRole,
                onRoleSelected = {
                    selectedRole = it
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(
                value = contactNumber,
                onValueChange = {
                    contactNumber = it
                },
                placeholder = "Contact Number"
            )

            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                placeholder = "Email Address"
            )

            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                placeholder = "Password",
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = buildAnnotatedString {

                    append("By creating an account you agree to our ")

                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("Terms of Service")
                    }

                    append(" and ")

                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("Privacy Policy")
                    }

                },
                fontSize = 10.sp,
                color = TextSecondary,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(
                text = "CREATE ACCOUNT",
                onClick = {
                    scope.launch {
                        try {
                            val response = RetrofitInstance.authApi.registerUser(
                                RegisterRequest(
                                    firstName = firstName,
                                    lastName = lastName,
                                    role = selectedRole,
                                    contactNumber = contactNumber,
                                    email = email,
                                    password = password
                                )
                            )

                            if (response.isSuccessful) {
                                println("Registration successful")
                                onGoToLogin()
                            } else {
                                println("Registration failed: ${response.errorBody()?.string()}")
                            }

                        } catch (e: Exception) {
                            println("Error: ${e.message}")
                        }
                    }
                }
            )

        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Already have an account? ",
                fontSize = 11.sp,
                color = TextSecondary
            )

            LinkText(
                text = "Sign In",
                onClick = onGoToLogin
            )

        }

        Spacer(modifier = Modifier.height(24.dp))

    }
}