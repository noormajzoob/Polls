package com.alnoor.polls.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.alnoor.polls.presentation.screen.login.LoginScreen
import com.alnoor.polls.presentation.screen.signup.SignupScreen

fun NavGraphBuilder.authNavigation(navHostController: NavHostController){
    composable(route = Destination.Login.route){
        LoginScreen(navHostController)
    }

    composable(route = Destination.Signup.route){
        SignupScreen(navHostController)
    }
}