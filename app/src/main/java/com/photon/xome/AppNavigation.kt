package com.photon.xome

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.navigationBarsPadding
import com.photon.xome.feature.home.view.HomeScreen
import com.photon.xome.utils.Screen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@Composable
fun AppNavigation() {
    val appNavController = rememberNavController()

    NavHost(navController = appNavController, startDestination = Screen.HomeScreen.route) {
        composable(Screen.HomeScreen.route){
            HomeScreen()
        }
    }
}
