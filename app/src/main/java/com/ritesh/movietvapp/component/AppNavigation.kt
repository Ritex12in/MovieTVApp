package com.ritesh.movietvapp.component

import HomeViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ritesh.movietvapp.screen.DetailsScreen
import com.ritesh.movietvapp.screen.HomeScreen
import com.ritesh.movietvapp.viewmodel.DetailsViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun AppNavigation(homeViewModel: HomeViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(homeViewModel) { titleId ->
                navController.navigate("details/$titleId")
            }
        }
        composable(
            route = "details/{titleId}",
            arguments = listOf(navArgument("titleId") { type = NavType.IntType })
        ) { backStackEntry ->
            val titleId = backStackEntry.arguments?.getInt("titleId") ?: 0
            val detailsViewModel:DetailsViewModel = getViewModel()
            DetailsScreen(titleId, detailsViewModel)
        }
    }
}
