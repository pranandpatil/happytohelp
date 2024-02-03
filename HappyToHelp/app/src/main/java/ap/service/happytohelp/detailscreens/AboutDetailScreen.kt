package ap.service.happytohelp.detailscreens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ap.service.happytohelp.R
import ap.service.happytohelp.ui.theme.GreenOriginal
import ap.service.happytohelp.ui.theme.HappyToHelpTheme
import java.lang.Float.min

@Composable
fun AboutDetailScreen(navController: NavController?) {
    HappyToHelpTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val scrollState = rememberScrollState()
            Box {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp)
                            .background(Color.White)
                            .graphicsLayer {
                                Log.e(
                                    "scroll",
                                    "${scrollState.value.toFloat()}, max = ${scrollState.maxValue}, ratio = ${(scrollState.value.toFloat() / scrollState.maxValue)}"
                                )
                                alpha =
                                    1f - ((scrollState.value.toFloat() / scrollState.maxValue) * 1.5f)
                                translationY = 0.5f * scrollState.value
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painterResource(id = R.drawable.about_abhi),
                            contentDescription = "about parallax",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Text(
                        text = stringResource(R.string.about_info),
                        modifier = Modifier.background(Color.White)
                            .padding(start = 20.dp, end = 20.dp, top = 30.dp, bottom = 100.dp),
                        color = Color.Black,
                        style = TextStyle(fontSize = 24.sp)
                    )
                }
                TopAppBar(
                    title = { Text("About") },
                    backgroundColor = MaterialTheme.colors.GreenOriginal,
                    contentColor = Color.White,
                    elevation = 12.dp,
                    modifier = Modifier
                        .alpha(min(1f, (scrollState.value.toFloat() / scrollState.maxValue) * 5f))
                        .background(MaterialTheme.colors.GreenOriginal),
                    navigationIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowBack, contentDescription = "Go Back",
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .clickable { navController!!.popBackStack() }
                        )
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun viewAboutScreen() {
    AboutDetailScreen(null)
}
