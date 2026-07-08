package cit.edu.garol.campus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import cit.edu.garol.campus.features.authentication.screens.LoginPage
import cit.edu.garol.campus.ui.theme.CampusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoginPage()
        }
    }
}