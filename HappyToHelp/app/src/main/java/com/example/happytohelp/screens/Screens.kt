package com.example.happytohelp.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector

object Screens {
    const val HomeNav = "Home_nav"
    const val Home = "Home"
    const val Contact = "Contact"
    const val More = "More"
    const val NotificationContent = "notification_content"

    const val PassportDetail = "passport_detail"
    const val MutualFundDetail = "mutual_fund_detail"
    const val VehicleINCDetail = "veh_inc_detail"
    const val TermINCDetail = "term_inc_detail"
    const val WealthManDetail = "wealth_man_detail"
    const val HealthINCDetail = "health_inc_detail"
    const val CriticalIllnessINCDetail = "critical_illness_inc_detail"

    const val SIPCalculator = "sip_Calculator"

    const val AboutDetail = "about_detail"

    const val GRAPH_HOME = "home_graph"
    const val GRAPH_DETAIL = "details_graph"
}

sealed class BottomNavItem(var title:String, var icon:ImageVector, var screen_route:String){

    object Home : BottomNavItem("Home", Icons.Default.Home,Screens.Home)
    object Contact: BottomNavItem("Contact",Icons.Default.Call,Screens.Contact)
    object More: BottomNavItem("More", Icons.Default.Menu,Screens.More)
}