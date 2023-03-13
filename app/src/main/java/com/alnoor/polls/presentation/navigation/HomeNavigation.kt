package com.alnoor.polls.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alnoor.polls.presentation.screen.create_poll.CreatePollScreen
import com.alnoor.polls.presentation.screen.home.HomeScreen
import com.alnoor.polls.presentation.screen.view_poll.ViewPollScreen

fun NavGraphBuilder.homeNavigation(navHostController: NavHostController){
    composable(route = Destination.Home.route){
        HomeScreen(navHostController)
    }

    composable(route = Destination.CreatePoll.route){
        CreatePollScreen(navHostController)
    }

    composable(
        route = Destination.ViewPoll.route,
        arguments = listOf(
            navArgument("poll"){
                type = NavType.StringType
                defaultValue = ""
            }
        )
    ){
        it.arguments?.getString("poll")?.let {
            ViewPollScreen(navHostController, it)
        }
    }
}