package cit.edu.garol.campus.screens

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
import cit.edu.garol.campus.components.CustomTextField
import cit.edu.garol.campus.components.LinkText
import cit.edu.garol.campus.components.PrimaryButton
import cit.edu.garol.campus.components.TopCurvedHeader
import cit.edu.garol.campus.ui.theme.Background
import cit.edu.garol.campus.ui.theme.Black
import cit.edu.garol.campus.ui.theme.Gold
import cit.edu.garol.campus.ui.theme.PrimaryButton
import cit.edu.garol.campus.ui.theme.TextSecondary

@Composable
fun LoginPage() {
    var idOrEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showRegister by remember { mutableStateOf(false) }

    if (showRegister) {
        RegisterPage(
            onGoToLogin = {
                showRegister = false
            }
        )
    } else {
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
                    onValueChange = { idOrEmail = it },
                    placeholder = "Enter your ID or Email",
                )

                Spacer(modifier = Modifier.height(10.dp))

                CustomTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "Enter your password",
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(20.dp))

                PrimaryButton(
                    text = "LOG IN",
                    onClick = {
                        // TODO: Connect this to Spring Boot login API later
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
                            //link ni dashboard successful login
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