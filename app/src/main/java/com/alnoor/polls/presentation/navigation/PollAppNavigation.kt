package com.alnoor.polls.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation

@Composable
fun PollAppNavigation(
    navHostController: NavHostController,
    isLogged: Boolean
){
    NavHost(
        navController = navHostController,
        startDestination =
        if (isLogged) Destination.HomeNav.route else
            Destination.AuthNav.route
    ){
        navigation(route = Destination.AuthNav.route, startDestination = Destination.Login.route){
            authNavigation(navHostController)
        }

        navigation(route = Destination.HomeNav.route, startDestination = Destination.Home.route){
            homeNavigation(navHostController)
        }
    }
}