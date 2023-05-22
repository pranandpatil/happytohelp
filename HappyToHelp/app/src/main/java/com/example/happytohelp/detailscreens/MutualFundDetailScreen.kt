package com.example.happytohelp.detailscreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.happytohelp.R
import com.example.happytohelp.screens.CaptureCustomerInfoDialogView
import com.example.happytohelp.screens.Screens
import com.example.happytohelp.ui.theme.GreenOriginal
import com.example.happytohelp.ui.theme.HappyToHelpTheme

@Composable
fun MutualFundDetailScreen(navController: NavController?) {
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
                DetailScreenAppBar("Mutual Funds", Icons.Default.ThumbUp)
                { navController!!.popBackStack() }
            }) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .background(Color.White)
                        .verticalScroll(rememberScrollState())
                ) {
                    Box( modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)) {
                        Text(
                            buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append(stringResource(id = R.string.mutual_fund_text))
                                }
                                append(stringResource(id = R.string.mutual_fund_info))
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append(stringResource(id = R.string.sip_text))
                                }
                                append(stringResource(id = R.string.sip_info))
                            },
                            color = Color.Black,
                            textAlign = TextAlign.Start,
                            fontSize = 18.sp
                        )
                    }

                    OutlinedButton(
                        onClick = { openCustomerInfoDialog.value = true },
                        modifier = Modifier
                            .align(alignment = Alignment.CenterHorizontally)
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 5.dp)
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(Color.White),
                        border = BorderStroke(2.dp, MaterialTheme.colors.GreenOriginal)
                    ) {
                        Text(
                            "ENQUIRE", fontSize = 18.sp,
                            color = MaterialTheme.colors.GreenOriginal
                        )
                    }

                    OutlinedButton(
                        onClick = { navController!!.navigate(Screens.SIPCalculator) },
                        modifier = Modifier
                            .align(alignment = Alignment.CenterHorizontally)
                            .padding(top = 5.dp, start = 16.dp, end = 16.dp, bottom = 5.dp)
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(Color.White),
                        border = BorderStroke(2.dp, MaterialTheme.colors.GreenOriginal)
                    ) {
                        Text(
                            "SIP CALCULATOR", fontSize = 18.sp,
                            color = MaterialTheme.colors.GreenOriginal
                        )
                    }

                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ViewMutualFundDetailScreen() {
    MutualFundDetailScreen(null)
}

