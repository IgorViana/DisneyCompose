package com.vianabrothers.android.myapplication.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {

        composable(route = "home") {
            Home(onCharacterSelected = { id ->
                navController.navigate("detail/$id")
            })
        }

        composable(
            route = "detail/{id}",
            arguments = listOf(navArgument(name = "id") {
                type = NavType.StringType
            })
        ) {
            // val arg = it.arguments?.getString("id").orEmpty()

            Detail() {
                navController.popBackStack()
            }
        }

    }
}
