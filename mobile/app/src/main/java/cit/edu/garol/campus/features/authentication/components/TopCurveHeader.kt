package cit.edu.garol.campus.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cit.edu.garol.campus.ui.theme.Maroon

@Composable
fun TopCurvedHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(155.dp)
            .background(
                color = Maroon,
                shape = GenericShape { size, _ ->
                    moveTo(0f, 0f)
                    lineTo(size.width, 0f)
                    lineTo(size.width, size.height * 0.55f)

                    quadraticBezierTo(
                        size.width / 2f,
                        size.height * 1.15f,
                        0f,
                        size.height * 0.55f
                    )

                    close()
                }
            )
    )
}