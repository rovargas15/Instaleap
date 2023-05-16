package com.example.instaflix.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.instaflix.domain.model.Film
import com.example.instaflix.ui.detail.screen.DetailScreen
import com.example.instaflix.ui.detail.viewmodel.DetailFilmViewModel
import com.example.instaflix.ui.home.screen.HomeScreen
import com.example.instaflix.ui.search.screen.SearchScreen
import com.example.instaflix.ui.utils.Category
import com.example.instaflix.ui.utils.Graph
import com.example.instaflix.ui.utils.Parameter
import com.example.instaflix.ui.utils.Route

@Composable
fun AppNavigation(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = Graph.MAIN_GRAPH) {
        mainGraph(
            navController = navController,
        )
    }
}

fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
) {
    navigation(startDestination = Route.POPULAR, route = Graph.MAIN_GRAPH) {
        composable(route = Route.POPULAR) {
            HomeScreen(Category.POPULAR, navController) { film: Film ->
                navController.navigate("${Route.FILM_DETAIL}${film.id}")
            }
        }

        composable(route = Route.UPCOMING) {
            HomeScreen(Category.UPCOMING, navController) { film: Film ->
                navController.navigate("${Route.FILM_DETAIL}${film.id}")
            }
        }

        composable(route = Route.PLAYING_NOW) {
            HomeScreen(Category.PLAYING_NOW, navController) { film: Film ->
                navController.navigate("${Route.FILM_DETAIL}${film.id}")
            }
        }

        composable(route = Route.SEARCH) {
            SearchScreen()
        }

        composable(
            route = "${Route.FILM_DETAIL}{${Parameter.FILM_ID}}",
            arguments = listOf(
                navArgument(Parameter.FILM_ID) { type = NavType.LongType },
            ),
        ) { backStackEntry ->
            val detailMovieViewModel: DetailFilmViewModel = hiltViewModel(backStackEntry)
            DetailScreen(detailMovieViewModel) {
                navController.popBackStack()
            }
        }
    }
}
