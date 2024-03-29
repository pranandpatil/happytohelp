package ap.service.happytohelp.detailscreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ap.service.happytohelp.screens.CaptureCustomerInfoDialogView
import ap.service.happytohelp.ui.theme.GreenOriginal
import ap.service.happytohelp.ui.theme.HappyToHelpTheme

@Composable
fun WealthManagementDetailScreen(navController: NavController?) {
    val context = LocalContext.current
    val openCustomerInfoDialog = remember { mutableStateOf(false) }
    HappyToHelpTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            if (openCustomerInfoDialog.value) {
                CaptureCustomerInfoDialogView(context) { openCustomerInfoDialog.value = false }
            }
            Scaffold(topBar = {
                DetailScreenAppBar("Financial Management", Icons.Default.ThumbUp)
                { navController!!.popBackStack() }
            }) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .background(Color.White)
                ) {
                    Box() {
                        Text(
                            text = "COMING SOON...",
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Start,
                            fontSize = 18.sp
                        )
                    }

                    OutlinedButton(
                        onClick = { openCustomerInfoDialog.value = true },
                        modifier = Modifier
                            .align(alignment = Alignment.CenterHorizontally)
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 5.dp)
                            .fillMaxWidth().alpha(0f),
                        colors = ButtonDefaults.buttonColors(Color.White),
                        border = BorderStroke(2.dp, MaterialTheme.colors.GreenOriginal),
                        enabled = false
                    ) {
                        Text(
                            "ENQUIRE", fontSize = 18.sp,
                            color = MaterialTheme.colors.GreenOriginal
                        )
                    }

                }
            }

        }
    }
}