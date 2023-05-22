package com.example.happytohelp.screens

import android.app.*
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.happytohelp.R
import com.example.happytohelp.detailscreens.*
import com.example.happytohelp.screens.Screens.GRAPH_DETAIL
import com.example.happytohelp.ui.theme.GreenOriginal
import com.example.happytohelp.ui.theme.HappyToHelpTheme


@Composable
fun HomeAppBar(title: String, image: ImageVector, iconClick: () -> Unit) {
    TopAppBar(
        title = { Text(title) },
        /*navigationIcon = {
            Icon(painter = painterResource(id = R.mipmap.ic_launcher), contentDescription = "Home",
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .clickable { iconClick.invoke() })
        },*/

        backgroundColor = MaterialTheme.colors.GreenOriginal,
        contentColor = Color.White,
        elevation = 12.dp,
        /*  actions = {
              Text(
                  text = "Marathi",
                  modifier = Modifier
                      .padding(horizontal = 10.dp)
                      .clickable { iconClick.invoke()},
                  fontFamily = FontFamily.SansSerif,
                  fontSize = 18.sp,
                  color = Color.Black
              )

          }*/

    )
}

@Composable
fun HomeScreen(navController: NavHostController?, paddingValues: PaddingValues) {
    Log.v("MainActivity", "HomeScreen")
    ServiceList(navController, paddingValues)
}

@Composable
fun ServiceList(navController: NavHostController?, paddingValues: PaddingValues) {
    Log.v("MainActivity", "HomeScreen : ServiceList")
    val context = LocalContext.current
    HappyToHelpTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = Color.White,
        ) {
            Scaffold(topBar = {
                HomeAppBar("Happy To Help Services", Icons.Default.ThumbUp) { }
            }
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        MenuCard(
                            R.drawable.indian_passports,
                            "Passport Service",
                            "New/renewal"
                        ) { navController!!.navigate(Screens.PassportDetail) }
                        MenuCard(
                            R.drawable.health_insurance,
                            "Health Insurance",
                            "Protect Yourself"
                        ) { navController!!.navigate(Screens.HealthINCDetail) }
                        MenuCard(
                            R.drawable.mutual_funds,
                            "Mutual Funds",
                            "Earn on Savings"
                        )
                        { navController!!.navigate(Screens.MutualFundDetail) }
                        /*MenuCard(
                            R.drawable.vehicle_insurance,
                            "Vehicle Insurance",
                            "Protect Your Vehicle"
                        ) { navController!!.navigate(Screens.VehicleINCDetail) }*/
                        MenuCard(
                            R.drawable.term_insurance,
                            "Term Insurance",
                            "Life Cover"
                        ) { navController!!.navigate(Screens.TermINCDetail) }
                        MenuCard(
                            R.drawable.wealth_manage,
                            "Financial Management",
                            "Plan Your Future"
                        )
                        { navController!!.navigate(Screens.WealthManDetail) }
                    }
                }

            }
        }
    }

}

@Composable
fun MenuCard(imageId: Int, title: String, subTitle: String, clickListener: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 8.dp, start = 10.dp, end = 10.dp, bottom = 8.dp)
            .clickable { clickListener.invoke() },
        elevation = 15.dp,
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .height(85.dp)
                    .wrapContentWidth()
                    .weight(0.4f),
                contentScale = ContentScale.FillBounds,
                painter = painterResource(imageId),
                contentDescription = "Passport"
            )
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .weight(0.6f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    title,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Text(
                    text = subTitle,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }
    }
}


fun NavGraphBuilder.homeDetailNavigationGraph(homeNavController: NavHostController?) {
    navigation(startDestination = Screens.PassportDetail, route = GRAPH_DETAIL) {

        composable(route = Screens.PassportDetail) {
            PassportDetailScreen(navController = homeNavController)
        }

        composable(route = Screens.HealthINCDetail) {
            HealthInsuranceDetailScreen(navController = homeNavController)
        }

        composable(route = Screens.MutualFundDetail) {
            MutualFundDetailScreen(navController = homeNavController)
        }

        composable(route = Screens.VehicleINCDetail) {
            VehicleInsuranceDetailScreen(navController = homeNavController)
        }
        composable(route = Screens.TermINCDetail) {
            TermInsuranceDetailScreen(navController = homeNavController)
        }

        composable(route = Screens.WealthManDetail) {
            WealthManagementDetailScreen(navController = homeNavController)
        }

        composable(route = Screens.AboutDetail) {
            AboutDetailScreen(navController = homeNavController)
        }

        composable(route = Screens.SIPCalculator) {
            SIPCalculatorScreen(navController = homeNavController)
        }
    }
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun PreviewView() {
    HomeScreen(null, PaddingValues(0.dp))
}

