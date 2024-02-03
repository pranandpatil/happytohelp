package ap.service.happytohelp.screens


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import ap.service.happytohelp.BuildConfig
import ap.service.happytohelp.ui.theme.HappyToHelpTheme
import java.io.ByteArrayOutputStream


@Composable
fun MoreScreen(navController: NavController?, paddingValues: PaddingValues) {
    val context = LocalContext.current
    // Dialog state Manager
    val openDialog = remember { mutableStateOf(false) }
    HappyToHelpTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), color = MaterialTheme.colors.background
        ) {
            if (openDialog.value) {
                CardDialogView(context) { openDialog.value = false }
            }
            Column(
                modifier = Modifier
                    .background(Color.White)
            ) {
                Image(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(ap.service.happytohelp.R.drawable.more_page),
                    contentDescription = "Contact Us"
                )
                Box(modifier = Modifier.clickable { navController!!.navigate(Screens.AboutDetail) }) {
                    Text(
                        text = "About",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        textAlign = TextAlign.Start,
                        fontFamily = FontFamily.SansSerif,
                        color = Color.Black,
                        fontSize = 18.sp
                    )
                }


                Divider(
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, bottom = 5.dp)
                        .height(1.dp)
                )

                Box(modifier = Modifier.clickable { openDialog.value = true }) {
                    Text(
                        modifier = Modifier
                            .padding(14.dp)
                            .fillMaxWidth(),
                        text = "Business Card",
                        textAlign = TextAlign.Start,
                        fontFamily = FontFamily.SansSerif,
                        color = Color.Black,
                        fontSize = 18.sp
                    )
                }

                Divider(
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, bottom = 5.dp)
                        .height(1.dp)
                )
            }
            Text(
                modifier = Modifier
                    .padding(14.dp)
                    .wrapContentHeight(Alignment.Bottom),
                text = "Version - ${ap.service.happytohelp.BuildConfig.VERSION_CODE}.${ap.service.happytohelp.BuildConfig.VERSION_NAME}",
                textAlign = TextAlign.Start,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
fun CardDialogView(context: Context, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss.invoke() }) {
        Box(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 20.dp),
        ) {


            Image(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                painter = painterResource(ap.service.happytohelp.R.drawable.buz_card),
                contentDescription = "Buz Card"
            )

            val offsetInPx = with(LocalDensity.current) {
                10.dp.roundToPx()
            }
            Icon(imageVector = Icons.Filled.Share, contentDescription = "Share",
                modifier = Modifier
                    .offset {
                        IntOffset(offsetInPx, -offsetInPx)
                    }
                    .align(alignment = Alignment.TopEnd)
                    .background(color = Color.LightGray, shape = CircleShape)
                    .padding(5.dp)
                    .clickable { shareBusinessCard(context) })

        }
    }

}

fun shareBusinessCard(context : Context){
    val b: Bitmap = BitmapFactory.decodeResource(context.resources, ap.service.happytohelp.R.drawable.buz_card)
    val share = Intent(Intent.ACTION_SEND)
    share.type = "image/jpeg"
    val bytes = ByteArrayOutputStream()
    b.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(context.contentResolver, b, "Title", null)
    val imageUri: Uri = Uri.parse(path)
    share.putExtra(Intent.EXTRA_STREAM, imageUri)
    context.startActivity(Intent.createChooser(share, "Select"))
}


@Preview
@Composable
fun ViewMoreScreen() {
    MoreScreen(null, PaddingValues(0.dp))
    //CardDialogView { }
}