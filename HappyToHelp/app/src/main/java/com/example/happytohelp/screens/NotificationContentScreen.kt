package com.example.happytohelp.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.happytohelp.ui.theme.HappyToHelpTheme

@Composable
fun NotificationContentScreen(navController: NavController?, title: String, body: String) {
    val openDialog = remember { mutableStateOf(true) }
    HappyToHelpTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier
                .fillMaxSize(), color = MaterialTheme.colors.background
        ) {
            Log.i("NotificationContentScreen", "notification Dialog: ${openDialog.value}")
            if (openDialog.value) {
                NotificationContentDialogView(title, body) {
                    openDialog.value = false
                    //navController!!.popBackStack()
                }
            }

        }
    }
}

@Composable
fun NotificationContentDialogView(title: String, body: String, onDismiss: () -> Unit) {
    Dialog(properties = DialogProperties(
        dismissOnClickOutside = false,
        usePlatformDefaultWidth = false
    ), onDismissRequest = { onDismiss.invoke() }) {
        Card(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .padding(20.dp),
            backgroundColor = Color.White,
            elevation = 8.dp
        ) {
            Column() {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close",
                    modifier = Modifier
                        .align(alignment = androidx.compose.ui.Alignment.End)
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                        .clickable { onDismiss.invoke() })
                Text(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 10.dp),
                    text = title,
                    fontFamily = FontFamily.Default,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 10.dp),
                    text = body,
                    fontFamily = FontFamily.Default,
                    color = Color.Black,
                    fontSize = 18.sp
                )
            }

        }
    }

}

@Composable
@Preview
fun PreviewDialog() {
    NotificationContentScreen(
        null, "Claim Approved",
        "You can then use this deepLinkPendingIntent like any other PendingIntent to open your app at the deep link destination."
    )
}