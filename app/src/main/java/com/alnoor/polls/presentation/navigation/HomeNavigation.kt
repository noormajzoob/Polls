package com.alnoor.polls.presentation.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.alnoor.polls.presentation.screen.create_poll.CreatePollScreen
import com.alnoor.polls.presentation.screen.home.HomeScreen
import com.alnoor.polls.presentation.screen.view_poll.ViewPollScreen
import com.alnoor.polls.presentation.screen.vote.VoteScreen
import com.alnoor.polls.util.Constant

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

    composable(
        route = Destination.VotePoll.route,
        deepLinks = listOf(
            navDeepLink { uriPattern = "${Constant.VOTE_DEEP_LINK}?id={id}" }
        )
    ){
        it.arguments?.getString("id")?.let {
            VoteScreen(it, navHostController)
        }
    }
}