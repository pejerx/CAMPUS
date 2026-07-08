package cit.edu.garol.campus.features.authentication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cit.edu.garol.campus.features.authentication.components.CustomTextField
import cit.edu.garol.campus.features.authentication.components.LinkText
import cit.edu.garol.campus.features.authentication.components.PrimaryButton
import cit.edu.garol.campus.features.authentication.components.TopCurvedHeader
import cit.edu.garol.campus.features.authentication.model.LoginRequest
import cit.edu.garol.campus.core.network.RetrofitInstance
import cit.edu.garol.campus.features.dashboard.screens.UserDashboardPage
import cit.edu.garol.campus.ui.theme.Background
import cit.edu.garol.campus.ui.theme.Black
import cit.edu.garol.campus.ui.theme.TextSecondary
import kotlinx.coroutines.launch

@Composable
fun LoginPage() {
    var idOrEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showRegister by remember { mutableStateOf(false) }
    var showUserDashboard by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()



    when {
        showUserDashboard -> {
            UserDashboardPage(
                onLogout = {
                    showUserDashboard = false
                    idOrEmail = ""
                    password = ""
                }
            )
        }

        showRegister -> {
            RegisterPage(
                onGoToLogin = {
                    showRegister = false
                }
            )
        }

        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopCurvedHeader()

                Spacer(modifier = Modifier.height(18.dp))

                Row {
                    Text(
                        text = "Welcome ",
                        fontSize = 15.sp,
                        color = TextSecondary
                    )

                    Text(
                        text = "Wildcats!",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        color = Black
                    )
                }

                Spacer(modifier = Modifier.height(95.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 42.dp)
                ) {
                    Text(
                        text = "Login into CAMPUS",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Black
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomTextField(
                        value = idOrEmail,
                        onValueChange = {
                            idOrEmail = it
                            errorMessage = ""
                        },
                        placeholder = "Enter your ID or Email"
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    CustomTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            errorMessage = ""
                        },
                        placeholder = "Enter your password",
                        visualTransformation = PasswordVisualTransformation()
                    )

                    if (errorMessage.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = errorMessage,
                            fontSize = 12.sp,
                            color = androidx.compose.ui.graphics.Color.Red
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    PrimaryButton(
                        text = if (isLoading) "LOGGING IN..." else "LOG IN",
                        onClick = {
                            if (idOrEmail.isBlank() || password.isBlank()) {
                                errorMessage = "Please enter your ID/email and password."
                                return@PrimaryButton
                            }

                            scope.launch {
                                isLoading = true
                                errorMessage = ""

                                try {
                                    val response = RetrofitInstance.authApi.loginUser(
                                        LoginRequest(
                                            id = idOrEmail,
                                            password = password
                                        )
                                    )

                                    if (response.isSuccessful) {
                                        showUserDashboard = true
                                    } else {
                                        errorMessage =
                                            response.errorBody()?.string()
                                                ?: "Login failed. Please try again."
                                    }

                                } catch (e: Exception) {
                                    errorMessage = e.message ?: "Unable to connect to server."
                                } finally {
                                    isLoading = false
                                }
                            }
                        },
                        modifier = Modifier
                            .width(160.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Continue as Guest",
                        fontSize = 11.sp,
                        color = Black,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .clickable {
                                showUserDashboard = true
                            }
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.padding(bottom = 28.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Don't have an account? ",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )

                    LinkText(
                        text = "Sign Up",
                        onClick = {
                            showRegister = true
                        }
                    )
                }
            }
        }
    }
}