package cit.edu.garol.campus.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(

    primary = Maroon,
    onPrimary = White,

    secondary = Gold,
    onSecondary = Black,

    background = Background,
    onBackground = Black,

    surface = Surface,
    onSurface = Black,

    error = Error,
    onError = White
)

private val DarkColorScheme = darkColorScheme(

    primary = Maroon,
    onPrimary = White,

    secondary = Gold,
    onSecondary = Black,

    background = Black,
    onBackground = White,

    surface = Black,
    onSurface = White,

    error = Error,
    onError = White
)

@Composable
fun CampusTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {

    val colors =
        if (darkTheme) {
            DarkColorScheme
        } else {
            LightColorScheme
        }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}