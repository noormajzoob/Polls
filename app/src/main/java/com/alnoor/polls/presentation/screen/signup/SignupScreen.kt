package com.alnoor.polls.presentation.screen.signup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
fun SignupScreen(
    navHostController: NavHostController,
    signupViewModel: SignupViewModel = hiltViewModel()
) {

    if(signupViewModel.uiState.signupSuccess){
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

            val (name, email, pass, login, error, signup, loading) = createRefs()
            val centerLine = createGuidelineFromTop(0.5f)

            OutlinedTextField(
                value = signupViewModel.uiState.userName,
                onValueChange = signupViewModel::setUserName,
                maxLines = 1,
                placeholder = { Text(text = "User name") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(name) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(email.top, 6.dp)
                    }
            )

            OutlinedTextField(
                value = signupViewModel.uiState.email,
                onValueChange = signupViewModel::setEmail,
                maxLines = 1,
                placeholder = { Text(text = "Email") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(email) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(centerLine, 6.dp)
                    }
            )

            OutlinedTextField(
                value = signupViewModel.uiState.password,
                onValueChange = signupViewModel::setPassword,
                maxLines = 1,
                placeholder = { Text(text = "Password") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                keyboardActions = KeyboardActions(onGo = {
                    signupViewModel.signup()
                }),
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(pass) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(centerLine)
                    }
            )

            Text(
                text = "Already have account? Login",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .constrainAs(signup) {
                        top.linkTo(pass.bottom, 6.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .clickable {
                        navHostController.popBackStack()
                    }
            )

            OutlinedButton(
                onClick = signupViewModel::signup,
                modifier = Modifier
                    .constrainAs(login) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(signup.bottom, 6.dp)
                    }
            ) {
                Text(text = "Sign up")
            }


            AnimatedVisibility(
                visible = signupViewModel.uiState.error != null,
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
                    text = signupViewModel.uiState.error?: "",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }

            AnimatedVisibility(
                visible = signupViewModel.uiState.loading,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier
                    .constrainAs(loading) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        if (signupViewModel.uiState.error != null){
                            top.linkTo(error.bottom, 6.dp)
                        }else top.linkTo(login.bottom, 6.dp)
                    }
            ) {
                CircularProgressIndicator()
            }
        }
    }
}