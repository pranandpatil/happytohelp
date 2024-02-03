package ap.service.happytohelp.detailscreens

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import ap.service.happytohelp.R
import ap.service.happytohelp.screens.CaptureCustomerInfoDialogView
import ap.service.happytohelp.ui.theme.BlueAz
import ap.service.happytohelp.ui.theme.GreenOriginal
import ap.service.happytohelp.ui.theme.HappyToHelpTheme


@Composable
fun PassportDetailScreen(navController: NavController?) {
    val context = LocalContext.current
    // Dialog state Manager
    val openCustomerInfoDialog = remember { mutableStateOf(false) }
    val openFeeStructureDialog = remember { mutableStateOf(false) }
    HappyToHelpTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            if (openCustomerInfoDialog.value) {
                CaptureCustomerInfoDialogView(context) { openCustomerInfoDialog.value = false }
            }

            if (openFeeStructureDialog.value) {
                FeeStructureDialogView(context) { openFeeStructureDialog.value = false }
            }

            Scaffold(topBar = {
                DetailScreenAppBar("Passport Service", Icons.Default.ThumbUp)
                { navController!!.popBackStack() }
            }) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .background(Color.White)
                        .verticalScroll(rememberScrollState())
                ) {

                    Text(
                        text = stringResource(id = R.string.passport_info),
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        textAlign = TextAlign.Start,
                        fontSize = 18.sp
                    )

                    Text(
                        text = "Government Fee Structure",
                        color = MaterialTheme.colors.BlueAz,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 10.dp, start = 16.dp, end = 16.dp)
                            .clickable { openFeeStructureDialog.value = true },
                        textAlign = TextAlign.Start,
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Italic,
                        textDecoration = TextDecoration.Underline
                    )


                    OutlinedButton(
                        onClick = { openCustomerInfoDialog.value = true },
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


@Composable
fun DetailScreenAppBar(title: String, image: ImageVector, iconClick: () -> Unit) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Go Back",
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .clickable { iconClick.invoke() }
            )
        },

        backgroundColor = MaterialTheme.colors.GreenOriginal,
        contentColor = Color.White,
        elevation = 12.dp,
    )
}


data class FeeItem(
    val nameText: String,
    val normalPrice: String,
    val tatkalPrice: String,
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FeeStructureDialogView(context: Context, onDismiss: () -> Unit) {

    Dialog(properties = DialogProperties(
        dismissOnClickOutside = true,
        usePlatformDefaultWidth = false
    ),
        onDismissRequest = { onDismiss.invoke() }) {
        Image(
            modifier = Modifier
                .wrapContentSize(),
            painter = painterResource(R.drawable.passport_fee),
            contentDescription = "Passport Fee",
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun FeeRowItem(feeItem: FeeItem) {
    Row(Modifier.fillMaxSize().height(IntrinsicSize.Min), verticalAlignment = Alignment.CenterVertically) {
        TableCell(text = feeItem.nameText.toString(), weight = .6f)
        TableCell(text = feeItem.normalPrice.toString(), weight = .2f)
        TableCell(text = feeItem.tatkalPrice.toString(), weight = .2f)
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Box(
        Modifier
            .fillMaxSize()
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(4.dp),
        propagateMinConstraints = true

    ) {
        Text(
            text = text,
            color = Color.Black
        )
    }

}

@Preview
@Composable
fun PreviewPassportDetailScreen() {
    PassportDetailScreen(null)
}