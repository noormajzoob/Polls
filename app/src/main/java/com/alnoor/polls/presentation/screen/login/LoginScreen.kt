package com.alnoor.polls.presentation.screen.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alnoor.polls.presentation.navigation.Destination

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
){

    if(loginViewModel.uiState.loginSuccess){
        navHostController.navigate(Destination.HomeNav.route){
            this.launchSingleTop = true
            this.popUpTo(Destination.HomeNav.route)
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(35.dp))
                .padding(horizontal = 16.dp, vertical = 32.dp)
                .align(Alignment.Center)
        ) {

            val (email, pass, login, error, signup, loading) = createRefs()
            val centerLine = createGuidelineFromTop(0.5f)

            OutlinedTextField(
                value = loginViewModel.uiState.email,
                onValueChange = loginViewModel::setEmail,
                maxLines = 1,
                placeholder = { Text(text = "Email")},
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = {}),
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(email) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(pass.top, 6.dp)
                    }
            )

            OutlinedTextField(
                value = loginViewModel.uiState.password,
                onValueChange = loginViewModel::setPassword,
                maxLines = 1,
                placeholder = { Text(text = "Password")},
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                keyboardActions = KeyboardActions(onGo = {
                    loginViewModel.login()
                }),
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(pass) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(centerLine, 6.dp)
                    }
            )

            Text(
                text = "You haven't account yet? signup",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(signup) {
                        top.linkTo(centerLine, 6.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .clickable {
                        navHostController.navigate(Destination.Signup.route)
                    }
            )

            OutlinedButton(
                onClick = loginViewModel::login,
                modifier = Modifier
                    .constrainAs(login) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(signup.bottom, 6.dp)
                    }
            ) {
                Text(text = "Login")
            }


            AnimatedVisibility(
                visible = loginViewModel.uiState.error != null,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier
                    .constrainAs(error) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(login.bottom, 6.dp)
                    }
            ) {
                Text(
                    text = loginViewModel.uiState.error?: "",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }

            AnimatedVisibility(
                visible = loginViewModel.uiState.loading,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier
                    .constrainAs(loading) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        if (loginViewModel.uiState.error != null){
                            top.linkTo(error.bottom, 6.dp)
                        }else top.linkTo(login.bottom, 6.dp)
                    }
                ) {
                CircularProgressIndicator()
            }
        }
    }

}