package cit.edu.garol.campus.features.authentication.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cit.edu.garol.campus.core.network.RetrofitInstance
import cit.edu.garol.campus.features.admin.model.AdminLoginRequest
import cit.edu.garol.campus.features.admin.screens.AdminDashboardScreen
import cit.edu.garol.campus.features.authentication.components.CustomTextField
import cit.edu.garol.campus.features.authentication.components.LinkText
import cit.edu.garol.campus.features.authentication.components.PrimaryButton
import cit.edu.garol.campus.features.authentication.components.TopCurvedHeader
import cit.edu.garol.campus.features.authentication.model.LoginRequest
import cit.edu.garol.campus.features.dashboard.screens.UserDashboardPage
import cit.edu.garol.campus.ui.theme.Background
import cit.edu.garol.campus.ui.theme.Black
import cit.edu.garol.campus.ui.theme.TextSecondary
import kotlinx.coroutines.launch

private val CampusMaroon = Color(0xFF7A0019)
private val CampusGold = Color(0xFFFFCC33)
private val SliderBackground = Color(0xFFE8E8E8)

private enum class LoginType {
    USER,
    ADMIN
}

@Composable
fun LoginPage() {
    var idOrEmail by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var loginType by remember {
        mutableStateOf(LoginType.USER)
    }

    var showRegister by remember {
        mutableStateOf(false)
    }

    var showUserDashboard by remember {
        mutableStateOf(false)
    }

    var showAdminDashboard by remember {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    when {
        showAdminDashboard -> {
            AdminDashboardScreen(
                onLogout = {
                    showAdminDashboard = false
                    loginType = LoginType.USER
                    idOrEmail = ""
                    password = ""
                    errorMessage = ""
                }
            )
        }

        showUserDashboard -> {
            UserDashboardPage(
                onLogout = {
                    showUserDashboard = false
                    loginType = LoginType.USER
                    idOrEmail = ""
                    password = ""
                    errorMessage = ""
                }
            )
        }

        showRegister -> {
            RegisterPage(
                onGoToLogin = {
                    showRegister = false
                    loginType = LoginType.USER
                    idOrEmail = ""
                    password = ""
                    errorMessage = ""
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

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

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

                Spacer(
                    modifier = Modifier.height(45.dp)
                )

                LoginTypeSlider(
                    selectedType = loginType,
                    onTypeSelected = { selectedType ->
                        loginType = selectedType
                        idOrEmail = ""
                        password = ""
                        errorMessage = ""
                    }
                )

                Spacer(
                    modifier = Modifier.height(32.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 42.dp)
                ) {
                    Text(
                        text = if (loginType == LoginType.ADMIN) {
                            "Login as Administrator"
                        } else {
                            "Login into CAMPUS"
                        },
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Black
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text = if (loginType == LoginType.ADMIN) {
                            "Enter your administrator credentials."
                        } else {
                            "Enter your user ID or registered email."
                        },
                        fontSize = 11.sp,
                        color = TextSecondary
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    CustomTextField(
                        value = idOrEmail,
                        onValueChange = {
                            idOrEmail = it
                            errorMessage = ""
                        },
                        placeholder = if (loginType == LoginType.ADMIN) {
                            "Enter administrator ID"
                        } else {
                            "Enter your ID or Email"
                        }
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

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
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        Text(
                            text = errorMessage,
                            fontSize = 12.sp,
                            color = Color.Red
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    PrimaryButton(
                        text = when {
                            isLoading && loginType == LoginType.ADMIN -> {
                                "LOGGING IN..."
                            }

                            isLoading -> {
                                "LOGGING IN..."
                            }

                            loginType == LoginType.ADMIN -> {
                                "ADMIN LOGIN"
                            }

                            else -> {
                                "LOG IN"
                            }
                        },
                        onClick = {
                            if (idOrEmail.isBlank() || password.isBlank()) {
                                errorMessage =
                                    if (loginType == LoginType.ADMIN) {
                                        "Please enter the administrator ID and password."
                                    } else {
                                        "Please enter your ID/email and password."
                                    }

                                return@PrimaryButton
                            }

                            if (isLoading) {
                                return@PrimaryButton
                            }

                            scope.launch {
                                isLoading = true
                                errorMessage = ""

                                try {
                                    when (loginType) {
                                        LoginType.USER -> {
                                            val response =
                                                RetrofitInstance.authApi.loginUser(
                                                    LoginRequest(
                                                        id = idOrEmail.trim(),
                                                        password = password
                                                    )
                                                )

                                            if (response.isSuccessful) {
                                                showUserDashboard = true
                                            } else {
                                                errorMessage =
                                                    readErrorMessage(
                                                        responseCode = response.code(),
                                                        errorBody = response
                                                            .errorBody()
                                                            ?.string(),
                                                        defaultMessage =
                                                        "User login failed."
                                                    )
                                            }
                                        }

                                        LoginType.ADMIN -> {
                                            val response =
                                                RetrofitInstance.adminApi.loginAdmin(
                                                    AdminLoginRequest(
                                                        emailOrId = idOrEmail.trim(),
                                                        password = password
                                                    )
                                                )

                                            if (response.isSuccessful) {
                                                val admin = response.body()

                                                if (
                                                    admin != null &&
                                                    admin.role.equals(
                                                        "ADMIN",
                                                        ignoreCase = true
                                                    )
                                                ) {
                                                    showAdminDashboard = true
                                                } else {
                                                    errorMessage =
                                                        "The server returned an invalid admin response."
                                                }
                                            } else {
                                                errorMessage =
                                                    response.errorBody()
                                                        ?.string()
                                                        ?.removePrefix("\"")
                                                        ?.removeSuffix("\"")
                                                        ?: when (response.code()) {
                                                            400 -> "Please enter the required credentials."
                                                            401 -> "Incorrect administrator ID or password."
                                                            403 -> "Administrator access denied."
                                                            404 -> "Admin login endpoint was not found."
                                                            500 -> "The server encountered an error."
                                                            else -> "Administrator login failed."
                                                        }
                                            }
                                        }
                                    }
                                } catch (exception: Exception) {
                                    errorMessage =
                                        exception.message
                                            ?: "Unable to connect to the server."
                                } finally {
                                    isLoading = false
                                }
                            }
                        },
                        modifier = Modifier
                            .width(170.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    if (loginType == LoginType.USER) {
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        Text(
                            text = "Continue as Guest",
                            fontSize = 11.sp,
                            color = Black,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .clickable(
                                    enabled = !isLoading
                                ) {
                                    showUserDashboard = true
                                }
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                if (loginType == LoginType.USER) {
                    Row(
                        modifier = Modifier.padding(
                            bottom = 28.dp
                        ),
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
                                if (!isLoading) {
                                    showRegister = true
                                }
                            }
                        )
                    }
                } else {
                    Text(
                        text = "Administrator access only",
                        fontSize = 11.sp,
                        color = TextSecondary,
                        modifier = Modifier.padding(
                            bottom = 28.dp
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun LoginTypeSlider(
    selectedType: LoginType,
    onTypeSelected: (LoginType) -> Unit
) {
    val sliderOffset by animateDpAsState(
        targetValue = if (selectedType == LoginType.USER) {
            0.dp
        } else {
            135.dp
        },
        label = "loginSliderOffset"
    )

    val userTextColor by animateColorAsState(
        targetValue = if (selectedType == LoginType.USER) {
            Color.White
        } else {
            Black
        },
        label = "userTextColor"
    )

    val adminTextColor by animateColorAsState(
        targetValue = if (selectedType == LoginType.ADMIN) {
            Color.White
        } else {
            Black
        },
        label = "adminTextColor"
    )

    Box(
        modifier = Modifier
            .width(270.dp)
            .height(48.dp)
            .clip(
                RoundedCornerShape(24.dp)
            )
            .background(SliderBackground)
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .width(127.dp)
                .height(40.dp)
                .offset(
                    x = sliderOffset
                )
                .clip(
                    RoundedCornerShape(20.dp)
                )
                .background(CampusMaroon)
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clickable {
                        onTypeSelected(LoginType.USER)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "USER",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = userTextColor
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clickable {
                        onTypeSelected(LoginType.ADMIN)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "ADMIN",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = adminTextColor
                )
            }
        }
    }
}

private fun readErrorMessage(
    responseCode: Int,
    errorBody: String?,
    defaultMessage: String
): String {
    if (!errorBody.isNullOrBlank()) {
        return errorBody
            .removePrefix("\"")
            .removeSuffix("\"")
    }

    return when (responseCode) {
        400 -> "Invalid login information."
        401 -> "Incorrect ID or password."
        403 -> "Access denied."
        404 -> "Account was not found."
        500 -> "The server encountered an error."
        else -> "$defaultMessage Error code: $responseCode"
    }
}