package com.alnoor.polls.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alnoor.polls.R
import com.alnoor.polls.presentation.commons.PollItemView
import com.alnoor.polls.presentation.commons.toJson
import com.alnoor.polls.presentation.navigation.Destination
import com.alnoor.polls.presentation.navigation.welcomeAnnotatedString
import com.alnoor.polls.ui.theme.tiltFont


@Composable
fun HomeScreen(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val uiState = homeViewModel.uiState

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ){
        if (uiState.loading){
            CircularProgressIndicator()
        }else if(uiState.error != null){
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
                OutlinedButton(onClick = homeViewModel::refresh) {
                    Text(
                        text = "Refresh"
                    )
                }
            }
        }else{
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                val (welcome, count, recentTitle, recent, bottomNav) = createRefs()

                Text(
                    text = welcomeAnnotatedString(name = homeViewModel.uiState.user?.name ?: ""),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.constrainAs(welcome){
                        top.linkTo(parent.top, 36.dp)
                        start.linkTo(parent.start, 16.dp)
                        end.linkTo(parent.end, 16.dp)

                        width = Dimension.fillToConstraints
                    }
                )

                ActivePollsCard(
                    count = homeViewModel.uiState.activePolls,
                    onClick = {  },
                    modifier = Modifier
                        .constrainAs(count){
                            start.linkTo(parent.start, 16.dp)
                            end.linkTo(parent.end, 16.dp)
                            top.linkTo(welcome.bottom, 24.dp)

                            width = Dimension.fillToConstraints
                        }
                )

                Text(
                    text = "Recent Polls",
                    style = MaterialTheme.typography.headlineSmall.tiltFont(),
                    modifier = Modifier.constrainAs(recentTitle){
                        top.linkTo(count.bottom, 36.dp)
                        start.linkTo(parent.start, 16.dp)
                    }
                )

                RecentPolls(
                    data = uiState.recentPoll,
                    modifier = Modifier.constrainAs(recent){
                        top.linkTo(recentTitle.bottom, 16.dp)
                        bottom.linkTo(bottomNav.top, 3.dp)
                        start.linkTo(parent.start, 16.dp)
                        end.linkTo(parent.end, 16.dp)

                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                    pollView = { poll ->
                        PollItemView(wrapper = poll, modifier = Modifier.fillMaxWidth()){
                            navHostController.navigate(Destination.ViewPoll.getRoute(poll))
                        }
                    }
                )



                BottomAppBar(
                    modifier = Modifier.constrainAs(bottomNav){
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)

                        width = Dimension.fillToConstraints
                    },
                    actions = {

                        NavigationBarItem(
                            selected = false,
                            onClick = { /*TODO*/ },
                            icon = {
                                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.poll), contentDescription = "Localized description")
                            },
                            label = {
                                Text("Polls")
                            }
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                navHostController.navigate(Destination.CreatePoll.route)
                            },
                            containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                        ) {
                            Icon(Icons.Filled.Add, "Localized description")
                        }
                    }
                )

            }
        }
    }

}