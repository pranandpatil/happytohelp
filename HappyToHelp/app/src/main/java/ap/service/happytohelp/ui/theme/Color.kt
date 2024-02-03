package ap.service.happytohelp.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val GreenOriginal444 = Color(0xFF7CC444)
val GreenLight58 = Color(0xFFDDFADA)
val GreenSemiLight = Color(0xFFA4E471)

val BlueAzure = Color(0xFF007FFF)

@get:Composable
val Colors.GreenOriginal : Color
    get() = GreenOriginal444

@get:Composable
val Colors.GreenLight : Color
    get() = GreenSemiLight

@get:Composable
val Colors.BlueAz : Color
    get() = BlueAzure