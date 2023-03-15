package com.alnoor.polls.presentation.screen.view_poll


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alnoor.polls.domain.model.PollChoose
import com.alnoor.polls.ui.theme.tiltFont

@Composable
fun ChoosesView(
    modifier: Modifier = Modifier,
    chooses: List<PollChoose>,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
) {

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(6.dp)
    ){
        items(
            count = chooses.size
        ){ index ->
            Text(
                text = "${index.inc()}- ${chooses[index].content}",
                style = MaterialTheme.typography.labelLarge.tiltFont(),
                color = textColor
            )
        }
    }

}