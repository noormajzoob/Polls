package com.alnoor.polls.presentation.screen.vote

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.alnoor.polls.domain.model.PollChoose
import com.alnoor.polls.domain.model.PollSelection
import com.alnoor.polls.ui.theme.tiltFont


@Composable
fun VotesChoosesList(
    modifier: Modifier = Modifier,
    data: List<PollChoose>,
    selected: PollChoose?,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    onSelect: (PollChoose)-> Unit
) {
    LazyColumn(
        modifier = modifier
    ){
        items(count = data.size){ index ->
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onSelect(data[index])
                    }
                    .padding(16.dp)

            ) {
                val (choose, radio) = createRefs()

                RadioButton(
                    selected = selected?.id == data[index].id,
                    onClick = { onSelect(data[index]) },
                    modifier = Modifier.constrainAs(radio){
                        start.linkTo(parent.start)
                    }
                )

                Text(
                    text = data[index].content,
                    style = MaterialTheme.typography.headlineSmall.tiltFont(),
                    color = textColor,
                    modifier = Modifier
                        .constrainAs(choose) {
                            start.linkTo(radio.end, 6.dp)
                            top.linkTo(radio.top)
                            bottom.linkTo(radio.bottom)
                        }
                )
            }
        }
    }
}