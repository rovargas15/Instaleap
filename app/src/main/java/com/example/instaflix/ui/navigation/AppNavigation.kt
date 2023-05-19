package com.example.instaflix.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.instaflix.ui.detail.screen.DetailScreen
import com.example.instaflix.ui.detail.viewmodel.DetailFilmViewModel
import com.example.instaflix.ui.home.screen.HomeScreen
import com.example.instaflix.ui.utils.Parameter
import com.example.instaflix.ui.utils.Route

@Composable
fun AppNavigation(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = Route.FILM) {
        composable(Route.FILM) {
            HomeScreen(navController) { id: Long ->
                navController.navigate("detail/$id?") {
                    popUpTo(Route.FILM) { inclusive = false }
                }
            }
        }

        composable(Route.SERIES) {
            HomeScreen(navController) { id: Long ->
                navController.navigate("detail/?$id") {
                    popUpTo(Route.SERIES) { inclusive = false }
                }
            }
        }

        composable(
            route = "${Route.FILM_DETAIL}{${Parameter.FILM_ID}}?{${Parameter.SERIES_ID}}",
            arguments = listOf(
                navArgument(Parameter.FILM_ID) { type = NavType.StringType; },
                navArgument(Parameter.SERIES_ID) { type = NavType.StringType; },
            ),
        ) { backStackEntry ->
            val detailMovieViewModel: DetailFilmViewModel = hiltViewModel(backStackEntry)
            DetailScreen(detailMovieViewModel) {
                navController.popBackStack()
            }
        }
    }
}
