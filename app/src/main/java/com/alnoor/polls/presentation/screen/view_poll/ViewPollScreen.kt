package com.alnoor.polls.presentation.screen.view_poll

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alnoor.polls.presentation.commons.PollItemView
import com.alnoor.polls.ui.theme.tiltFont

@Composable
fun ViewPollScreen(
    navHostController: NavHostController,
    poll: String,
    viewPollViewModel: ViewPollViewModel = hiltViewModel()
) {

    val uiState = viewPollViewModel.uiState
    if (uiState.poll == null) viewPollViewModel.parsePoll(pollJson = poll)

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

                if (uiState.errorType != null && uiState.errorType is ErrorType.NetworkError){
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedButton(onClick = viewPollViewModel::refresh) {
                        Text(
                            text = "Refresh"
                        )
                    }
                }

            }

        }
        else{
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (back, title, pollView, chooses, countTitle, votesList) = createRefs()

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

                uiState.poll?.chooses?.let {
                    ChoosesView(
                        chooses = it,
                        modifier = Modifier
                            .constrainAs(chooses) {
                                start.linkTo(parent.start, 25.dp)
                                end.linkTo(parent.end, 25.dp)
                                top.linkTo(pollView.bottom)

                                width = Dimension.fillToConstraints
                            }
                            .clip(RoundedCornerShape(bottomEnd = 15.dp, bottomStart = 15.dp))
                            .background(MaterialTheme.colorScheme.tertiaryContainer)
                            .padding(8.dp)
                    )
                }

                Text(
                    text = "${uiState.votes.size} votes",
                    style = MaterialTheme.typography.headlineSmall.tiltFont(),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .constrainAs(countTitle) {
                            start.linkTo(parent.start, 16.dp)
                            end.linkTo(parent.end, 16.dp)
                            top.linkTo(chooses.bottom, 16.dp)

                            width = Dimension.fillToConstraints
                        }
                )

                uiState.poll?.let {
                    VotesList(
                        data = uiState.votes,
                        choosesMap = it.chooses.mapIndexed { index, pollChoose -> pollChoose.id to index.inc() }.toMap(),
                        modifier = Modifier
                            .constrainAs(votesList) {
                                start.linkTo(parent.start, 16.dp)
                                end.linkTo(parent.end, 16.dp)
                                top.linkTo(countTitle.bottom, 16.dp)

                                width = Dimension.fillToConstraints
                            }.clip(RoundedCornerShape(15.dp))
                            .background(MaterialTheme.colorScheme.secondaryContainer),
                        textColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

            }
        }
    }

}