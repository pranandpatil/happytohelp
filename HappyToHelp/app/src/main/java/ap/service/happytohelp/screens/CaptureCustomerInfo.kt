package ap.service.happytohelp.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Patterns
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ap.service.happytohelp.ui.theme.GreenOriginal
import java.net.URLEncoder

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CaptureCustomerInfoDialogView(context: Context, onDismiss: () -> Unit) {
    var nameText by remember { mutableStateOf("") }
    var mobileText by remember { mutableStateOf("") }
    var emailText by remember { mutableStateOf("") }

    var isNameCorrect by remember { mutableStateOf(true) }
    var isMobileCorrect by remember { mutableStateOf(true) }
    var isEmailCorrect by remember { mutableStateOf(true) }

    Dialog(properties = DialogProperties(
        dismissOnClickOutside = false,
        usePlatformDefaultWidth = false
    ),
        onDismissRequest = { onDismiss.invoke() }) {
        Card(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = 8.dp,
            backgroundColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.White)
                    .verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    value = nameText,
                    onValueChange = {
                        nameText = it
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        textColor = Color.Black,
                        focusedBorderColor = MaterialTheme.colors.GreenOriginal,
                        unfocusedBorderColor = MaterialTheme.colors.GreenOriginal,
                        placeholderColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black
                    ),
                    label = { Text(text = "Name") },
                    placeholder = { Text(text = "Enter Full Name") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                if (!isNameCorrect) {
                    Text(
                        text = "Enter Name",
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    value = mobileText,
                    onValueChange = {
                        mobileText = it
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        textColor = Color.Black,
                        focusedBorderColor = MaterialTheme.colors.GreenOriginal,
                        unfocusedBorderColor = MaterialTheme.colors.GreenOriginal,
                        placeholderColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black
                    ),
                    label = { Text(text = "Mobile Number") },
                    placeholder = { Text(text = "Enter Mobile Number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )

                if (!isMobileCorrect) {
                    Text(
                        text = "Enter Proper Mobile Number",
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    value = emailText,
                    onValueChange = {
                        emailText = it
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        textColor = Color.Black,
                        focusedBorderColor = MaterialTheme.colors.GreenOriginal,
                        unfocusedBorderColor = MaterialTheme.colors.GreenOriginal,
                        placeholderColor = Color.Black,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black
                    ),
                    label = { Text(text = "Email") },
                    placeholder = { Text(text = "Enter Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                if (!isEmailCorrect) {
                    Text(
                        text = "Enter Proper Email",
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    OutlinedButton(
                        onClick = { onDismiss.invoke() },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .wrapContentWidth()
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(Color.White),
                        border = BorderStroke(1.dp, MaterialTheme.colors.GreenOriginal)
                    ) {
                        Text(
                            text = "Cancel", fontSize = 16.sp,
                            color = MaterialTheme.colors.GreenOriginal
                        )
                    }
                    OutlinedButton(
                        onClick = {
                            isNameCorrect = nameText.isNotEmpty()
                            isMobileCorrect = mobileText.isNotEmpty() && Patterns.PHONE.matcher(
                                mobileText
                            ).matches()
                            isEmailCorrect =
                                emailText.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(
                                    emailText
                                ).matches()

                            if (isNameCorrect && isMobileCorrect && isEmailCorrect) {
                                val megText =
                                    "Hi \nI want to enquire about Passport Services.\nBelow are my details -\nName - $nameText \nMobile - $mobileText \nEmail - $emailText \n[Sent from Android Mobile App]"

                                sentToWhatsApp(context, megText)
                            }
                        },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .wrapContentWidth()
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(Color.White),
                        border = BorderStroke(1.dp, MaterialTheme.colors.GreenOriginal)
                    ) {
                        Text(
                            text = "Submit", fontSize = 16.sp,
                            color = MaterialTheme.colors.GreenOriginal
                        )
                    }
                }
            }

        }
    }

}

fun sentToWhatsApp(context: Context, message: String) {
    val url = "https://api.whatsapp.com/send?phone=$+91 9766349388" + "&text=" + URLEncoder.encode(
        message,
        "UTF-8"
    )
    val sendIntent = Intent()
    sendIntent.action = Intent.ACTION_VIEW
    sendIntent.data = Uri.parse(url);
    context.startActivity(sendIntent)
}
