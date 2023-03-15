package com.alnoor.polls.presentation.screen.vote

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alnoor.polls.presentation.commons.PollItemView
import com.alnoor.polls.presentation.screen.create_poll.Action
import com.alnoor.polls.presentation.screen.create_poll.StateView
import com.alnoor.polls.ui.theme.tiltFont

@Composable
fun VoteScreen(
    pollId: String,
    navHostController: NavHostController,
    voteViewModel: VoteViewModel = hiltViewModel()
) {

    val uiState = voteViewModel.uiState

    if (!uiState.loading && uiState.poll == null) voteViewModel.getPoll(pollId)
    
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        if (uiState.loading){
            CircularProgressIndicator()
        }
        else if (uiState.error != null){

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = uiState.error,
                    style = MaterialTheme.typography.headlineSmall.tiltFont(),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(6.dp))
                OutlinedButton(onClick = voteViewModel::refresh) {
                    Text(
                        text = "Refresh"
                    )
                }

            }

        }else {

            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (back, title, pollView, chooses, state) = createRefs()

                Text(
                    text = "Poll",
                    style = MaterialTheme.typography.headlineSmall.tiltFont(),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .constrainAs(title) {
                            start.linkTo(back.end, 6.dp)
                            top.linkTo(back.top)
                            bottom.linkTo(back.bottom)
                        }
                )

                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(12.dp)
                        .constrainAs(back) {
                            start.linkTo(parent.start, 16.dp)
                            top.linkTo(parent.top, 16.dp)
                        }
                        .clickable {
                            navHostController.popBackStack()
                        }
                )

                uiState.poll?.let {
                    PollItemView(wrapper = it, modifier = Modifier
                        .constrainAs(pollView) {
                            start.linkTo(parent.start, 16.dp)
                            end.linkTo(parent.end, 16.dp)
                            top.linkTo(back.bottom, 16.dp)

                            width = Dimension.fillToConstraints
                        }
                        .clickable(enabled = false) {}, textColor = MaterialTheme.colorScheme.onTertiaryContainer){

                    }
                }

                uiState.poll?.chooses?.let { data ->
                    VotesChoosesList(
                        data = data,
                        selected = uiState.selectedChoose,
                        onSelect = voteViewModel::setChoose,
                        textColor = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.constrainAs(chooses){
                            start.linkTo(parent.start, 16.dp)
                            end.linkTo(parent.end, 16.dp)
                            top.linkTo(pollView.bottom, 16.dp)

                            width = Dimension.fillToConstraints
                        }
                    )
                }

                StateView(
                    isPosted = false,
                    isLoading = uiState.submitLoading,
                    message = uiState.submitMsg,
                    onAction = {
                               voteViewModel.postVote()
                    },
                    modifier = Modifier.constrainAs(state){
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)

                        width = Dimension.fillToConstraints
                    }
                )

            }

        }
    }
    
}