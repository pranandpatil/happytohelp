package com.example.happytohelp.screens


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.rounded.Call
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.happytohelp.R
import com.example.happytohelp.ui.theme.BlueAz
import com.example.happytohelp.ui.theme.HappyToHelpTheme

@Composable
fun ContactScreen(navController: NavController?, paddingValues: PaddingValues) {
    val context = LocalContext.current
    HappyToHelpTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    painter = painterResource(R.drawable.contact_us),
                    contentDescription = "Contact Us"
                )

                Row(
                    modifier = Modifier
                        .padding(
                            top = 40.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                        .fillMaxWidth()
                ) {

                    Icon(
                        imageVector = Icons.Rounded.Call, contentDescription = "Call",
                        modifier = Modifier
                            .align(alignment = Alignment.CenterVertically)
                    )
                    Column() {
                        Text(
                            "9766349388",
                            fontSize = 18.sp,
                            fontFamily = FontFamily.SansSerif,
                            fontStyle = FontStyle.Normal,
                            color = MaterialTheme.colors.BlueAz,
                            modifier = Modifier.padding(start = 8.dp)
                                .clickable {
                                openDialPad(context, "9766349388")
                            }
                        )
                        Text(
                            "7276349388",
                            fontSize = 18.sp,
                            fontFamily = FontFamily.SansSerif,
                            fontStyle = FontStyle.Normal,
                            color = MaterialTheme.colors.BlueAz,
                            modifier = Modifier.padding(start = 8.dp)
                                .clickable {
                                openDialPad(context, "9766349388")
                            }
                        )
                    }

                }

                Row(
                    modifier = Modifier
                        .padding(
                            top = 20.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Email, contentDescription = "Call",
                        modifier = Modifier
                            .align(alignment = Alignment.CenterVertically)
                    )
                    Text(
                        text = "alwayshappytoohelp@gmail.com",
                        modifier = Modifier.padding(start = 8.dp)
                            .clickable {
                                openEmail(context, "alwayshappytoohelp@gmail.com")
                            },
                        fontSize = 18.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontStyle = FontStyle.Normal,
                        color = MaterialTheme.colors.BlueAz,
                    )
                }

                Text(
                    text = "Address -",
                    modifier = Modifier.padding(
                        top = 20.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 8.dp
                    ),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontStyle = FontStyle.Normal
                )

                Text(
                    text = "Chandresh Apartment, Flat No G-01, Near Gajanan Maharaj Mandir, Neminathnagar Ground, Vishrambag, Sangli 416416",
                    modifier = Modifier.padding(
                        top = 8.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    ),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontStyle = FontStyle.Normal
                )

                Text(
                    "Follow us on :-",
                    modifier = Modifier
                        .padding(
                            top = 50.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 8.dp
                        )
                        .fillMaxWidth(),
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontStyle = FontStyle.Normal,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier
                        .padding(
                            top = 10.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        modifier = Modifier
                            .size(35.dp)
                            .clickable { openFbPage(context) },
                        painter = painterResource(id = R.drawable.facebook_logo_icon),
                        contentDescription = "Facebook"
                    )

                    Image(
                        modifier = Modifier
                            .size(35.dp)
                            .clickable { openYouTubePage(context) },
                        painter = painterResource(id = R.drawable.youtube_logo_icon),
                        contentDescription = "YouTube"
                    )

                    Image(
                        modifier = Modifier
                            .size(35.dp)
                            .clickable { openInstagramPage(context) },
                        painter = painterResource(id = R.drawable.instagram_logo_icon),
                        contentDescription = "Instagram"
                    )

                    Image(
                        modifier = Modifier
                            .size(35.dp)
                            .clickable { openLinkedInPage(context) },
                        painter = painterResource(id = R.drawable.linkedin_logo_icon),
                        contentDescription = "LinkedIn"
                    )
                }
            }
        }
    }
}

fun openDialPad(context: Context, phoneNum: String) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$phoneNum")
    try {
        context.startActivity(intent)
    } catch (s: SecurityException) {
        Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT)
            .show()
    }
    context.startActivity(intent)
}

fun openEmail(context: Context, email: String) {
    val i = Intent(Intent.ACTION_SEND)
    val emailAddress = arrayOf(email)
    i.putExtra(Intent.EXTRA_EMAIL, emailAddress)
    //i.putExtra(Intent.EXTRA_SUBJECT, emailSubject.value.text)
    //i.putExtra(Intent.EXTRA_TEXT, emailBody.value.text)
    i.type = "message/rfc822"
    context.startActivity(Intent.createChooser(i, "Choose an Email client : "))

}

fun openFbPage(context: Context) {
    val fbPageURL = "https://www.facebook.com/profile.php?id=1000672211896818&mibextid=ZbWKwL"
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(fbPageURL))
    context.startActivity(browserIntent)
}

fun openYouTubePage(context: Context) {
    val youTubePageURL = "https://youtube.com/@happytohelpservices1752"
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(youTubePageURL))
    context.startActivity(browserIntent)

}

fun openInstagramPage(context: Context) {
    /*val instagramPageURL = "https://www.facebook.com/n/?YOUR_PAGE_NAME"
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(instagramPageURL))
    context.startActivity(browserIntent)*/
}

fun openLinkedInPage(context: Context) {
    /*val linkedInPageURL = "https://www.facebook.com/n/?YOUR_PAGE_NAME"
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedInPageURL))
    context.startActivity(browserIntent)*/
}

@Preview
@Composable
fun ContactViewPreview() {
    ContactScreen(null, PaddingValues(0.dp))
}
