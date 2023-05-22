package com.example.happytohelp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.happytohelp.screens.*
import com.example.happytohelp.screens.Screens.GRAPH_HOME
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v(MainActivity::class.java.name, "onCreate")
        super.onCreate(savedInstanceState)
        Firebase.messaging.subscribeToTopic("happy").addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.v(MainActivity::class.java.name, "Topic - Happy subscribed")
            } else {
                Log.v(MainActivity::class.java.name, "Topic -  Happy FAILED")
            }
        }
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White
            ) {
                MainScreenView(intent)
            }
        }

    }
}

@Composable
fun HomeNavigationGraph(navController: NavHostController, padding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home,
        route = GRAPH_HOME
    ) {

        composable(route = Screens.Home) {
            HomeScreen(navController = navController, padding)
        }

        composable(route = Screens.Contact) {
            ContactScreen(navController = navController, padding)
        }

        composable(route = Screens.More) {
            MoreScreen(navController = navController, padding)
        }

        /*composable(
            route = "${Screens.NotificationContent}/{text_message}",
            arguments = listOf(navArgument("text_message") { type = NavType.StringType }),
            deepLinks = listOf(navDeepLink {
                uriPattern = "app://com.example.happy/text_message={text_message}"
                action = ACTION_VIEW
            })
        ) { backStackEntry ->
            Log.i("NotificationContentScreen", "going to notification Dialog")
            val arguments = backStackEntry.arguments
            NotificationContentScreen(
                navController = navController,
                padding,
                arguments?.getString("text_message")!!
            )

        }*/
        homeDetailNavigationGraph(navController)
        // moreDetailNavigationGraph(navController)
    }
}


@Composable
fun MainScreenView(intent: Intent) {
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = Color.White)
    val navController = rememberNavController()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf<String>(Manifest.permission.POST_NOTIFICATIONS),
                11
            )
        }
    }

    val notificationTitle = remember {
        mutableStateOf(
            if (intent.hasExtra("title")) {
                intent.getStringExtra("title")
            } else {
                ""
            }
        )
    }

    val notificationBody = remember {
        mutableStateOf(
            if (intent.hasExtra("body")) {
                intent.getStringExtra("body")
            } else {
                ""
            }
        )
    }

    Log.i(MainActivity::class.java.name, "Notification Title = ${notificationTitle.value!!}")
    Log.i(MainActivity::class.java.name, "Notification Body = ${notificationBody.value!!}")

    if (!notificationTitle.value.isNullOrEmpty() && !notificationBody.value.isNullOrEmpty()) {
        //navController.popBackStack(Screens.Home, true)
        NotificationContentScreen(
            navController = navController,
            notificationTitle.value!!, notificationBody.value!!
        )
    }

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        HomeNavigationGraph(navController = navController, it)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Contact,
        BottomNavItem.More,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarDestination = items.any { it.screen_route == currentDestination?.route }
    if (bottomBarDestination) {
        BottomNavigation(
            backgroundColor = colorResource(id = R.color.white),
            contentColor = Color.Black
        ) {
            items.forEach { item ->
                BottomNavigationItem(
                    icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                    label = {
                        Text(
                            text = item.title,
                            fontSize = 10.sp
                        )
                    },
                    selectedContentColor = Color.Black,
                    unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
                    alwaysShowLabel = true,
                    selected = currentDestination?.hierarchy?.any {
                        it.route == item.screen_route
                    } == true,
                    onClick = {
                        /*navController.navigate(item.screen_route) {

                            navController.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }*/
                        navController.navigate(item.screen_route) {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }

}