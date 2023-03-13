package com.alnoor.polls.presentation.screen.create_poll

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alnoor.polls.R
import com.alnoor.polls.presentation.navigation.Destination
import com.alnoor.polls.ui.theme.tiltFont

@Composable
fun CreatePollScreen(
    navHostController: NavHostController,
    createPollViewModel: CreatePollViewModel = hiltViewModel()
) {
    val uiState = createPollViewModel.uiState

    BackHandler {
        if (uiState.postPollSuccess){
            navHostController.navigate(Destination.Home.route){
                popUpTo(Destination.Home.route){
                    inclusive = true
                }
            }
        }else navHostController.popBackStack()
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {

        val (back, title, pollTitle, chooseTitle, choosesList, durationTitle, duration, bottomNav) = createRefs()

        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .padding(12.dp)
                .constrainAs(back){
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top, 16.dp)
                }.clickable {
                    if (uiState.postPollSuccess){
                        navHostController.navigate(Destination.Home.route){
                            popUpTo(Destination.Home.route){
                                inclusive = true
                            }
                        }
                    }else navHostController.popBackStack()
                }
        )

        Text(
            text = "Create Poll",
            style = MaterialTheme.typography.headlineMedium.tiltFont(),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .constrainAs(title) {
                    start.linkTo(back.end, 6.dp)
                    end.linkTo(parent.end, 16.dp)
                    top.linkTo(back.top)
                    bottom.linkTo(back.bottom)

                    width = Dimension.fillToConstraints
                }
        )

        OutlinedTextField(
            value = uiState.pollTitle,
            onValueChange = createPollViewModel::setPollTitle,
            maxLines = 1,
            placeholder = { Text(text = "Poll Title") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {}),
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(pollTitle) {
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    top.linkTo(title.bottom, 16.dp)

                    width = Dimension.fillToConstraints
                }
        )

        Text(
            text = "Duration",
            style = MaterialTheme.typography.headlineMedium.tiltFont(),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .constrainAs(durationTitle) {
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(duration.top)
                    bottom.linkTo(duration.bottom)
                }
        )

        DurationEditor(
            value = uiState.pollDuration,
            onValueChange = createPollViewModel::setPollDuration,
            modifier = Modifier
                .constrainAs(duration) {
                    start.linkTo(durationTitle.end, 16.dp)
                    top.linkTo(pollTitle.bottom, 16.dp)
                }
        )

        Text(
            text = "Add chooses",
            style = MaterialTheme.typography.headlineSmall.tiltFont(),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .constrainAs(chooseTitle) {
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    top.linkTo(duration.bottom, 6.dp)

                    width = Dimension.fillToConstraints
                }
        )

        ChoosesList(
            data = uiState.pollChooses,
            onChooseValueChange = createPollViewModel::setChose,
            onChooseEditDone = createPollViewModel::setChooseAsEdited,
            onSetChooseAsEdited = createPollViewModel::setChooseAsEditable,
            modifier = Modifier
                .constrainAs(choosesList) {
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    top.linkTo(chooseTitle.bottom, 6.dp)

                    width = Dimension.fillToConstraints
                }
        )

        StateView(
            isPosted = uiState.postPollSuccess,
            isLoading = uiState.loading,
            message = uiState.error,
            onAction = { action ->
                if (action is Action.Send) createPollViewModel.submitPoll()
            },
            modifier = Modifier.constrainAs(bottomNav){
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)

                width = Dimension.fillToConstraints
            }
        )

    }

}