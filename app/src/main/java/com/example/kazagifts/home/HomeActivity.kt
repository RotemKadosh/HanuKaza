package com.example.kazagifts.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kazagifts.auth.AuthActivity.Companion.CLIENT_ID
import com.example.kazagifts.home.currentraffle.CurrentRaffleScreen
import com.example.kazagifts.home.currentraffle.CurrentRaffleViewModel
import com.example.kazagifts.home.history.RafflesHistoryScreen
import com.example.kazagifts.ui.theme.KazaGiftsTheme

class HomeActivity: ComponentActivity() {

    private val currentRaffleViewModel: CurrentRaffleViewModel by viewModels()

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val accountId = intent?.getStringExtra(CLIENT_ID)
        if (accountId != "0"){
            currentRaffleViewModel.onAccountUpdate(accountId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen()
        }
    }

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen() {
    KazaGiftsTheme {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { BottomNavigation(navController = navController) }
        ) {
            NavigationGraph(navController = navController)
        }
    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavBarItem.CurrentRaffleScreen,
        BottomNavBarItem.RafflesHistoryScreen
    )
    BottomNavigation( modifier = Modifier.wrapContentSize(),
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                modifier = Modifier.wrapContentSize(),
                icon = { Icon(painterResource(id = item.icon),modifier = Modifier.fillMaxSize(0.8f).padding(3.dp), contentDescription = item.title) },
                label = { Text(text = item.title, fontSize = 9.sp) },
                selectedContentColor = MaterialTheme.colors.onPrimary,
                unselectedContentColor = MaterialTheme.colors.onPrimary.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavBarItem.CurrentRaffleScreen.screen_route) {
        composable(BottomNavBarItem.CurrentRaffleScreen.screen_route) {
            CurrentRaffleScreen()
        }
        composable(BottomNavBarItem.RafflesHistoryScreen.screen_route) {
            RafflesHistoryScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    HomeScreen()
}