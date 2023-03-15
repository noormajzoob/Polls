package com.alnoor.polls.presentation.screen.view_poll

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.alnoor.polls.domain.model.PollSelection
import com.alnoor.polls.ui.theme.tiltFont

@Composable
fun VotesList(
    modifier: Modifier = Modifier,
    data: List<PollSelection>,
    choosesMap: Map<Long, Int>,
    textColor: Color = MaterialTheme.colorScheme.onBackground
) {
    LazyColumn(
        modifier = modifier
    ){
        items(count = data.size){ index ->
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp)
            ) {
                val (userName, voteId) = createRefs()
                val center = createGuidelineFromStart(0.5f)

                Text(
                    text = data[index].user.name,
                    style = MaterialTheme.typography.labelLarge.tiltFont(),
                    color = textColor,
                    modifier = Modifier
                        .constrainAs(userName) {
                            start.linkTo(parent.start)
                            end.linkTo(center)
                        }
                )

                Text(
                    text = "Voted ${choosesMap[data[index].choose.id]}",
                    style = MaterialTheme.typography.labelLarge.tiltFont(),
                    color = MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.7f
                    ),
                    modifier = Modifier
                        .constrainAs(voteId) {
                            end.linkTo(parent.end)
                        }
                )
            }
        }
    }
}