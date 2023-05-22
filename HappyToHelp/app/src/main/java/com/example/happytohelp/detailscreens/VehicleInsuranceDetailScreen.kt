package com.example.happytohelp.detailscreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.happytohelp.screens.CaptureCustomerInfoDialogView
import com.example.happytohelp.ui.theme.GreenOriginal
import com.example.happytohelp.ui.theme.HappyToHelpTheme

@Composable
fun VehicleInsuranceDetailScreen(navController: NavController?) {
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
                DetailScreenAppBar("Vehicle Insurance", Icons.Default.ThumbUp)
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
                            text = "We provide passport service for\n Fresh\n Renewal\n Tatkal\n Kids\n Lost\n Damaged\n PCC",
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Start,
                            fontSize = 18.sp
                        )
                    }

                    OutlinedButton(
                        onClick = { /*openCustomerInfoDialog.value = true*/ },
                        modifier = Modifier
                            .align(alignment = Alignment.CenterHorizontally)
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 5.dp)
                            .fillMaxWidth(),
                        colors = buttonColors(Color.White),
                        border = BorderStroke(2.dp, MaterialTheme.colors.GreenOriginal)
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