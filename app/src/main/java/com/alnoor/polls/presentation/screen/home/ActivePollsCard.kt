package com.alnoor.polls.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.alnoor.polls.ui.theme.tiltFont

@Composable
fun ActivePollsCard(
    modifier: Modifier = Modifier,
    count: Int,
    onClick: ()-> Unit,
    textStyle: TextStyle = MaterialTheme.typography.headlineSmall,
    textColor: Color = MaterialTheme.colorScheme.onTertiaryContainer,
    backgroundColor: Color = MaterialTheme.colorScheme.tertiaryContainer
) {

    ConstraintLayout(
        modifier = modifier
            .wrapContentHeight()
            .clip(RoundedCornerShape(35.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(16.dp)
    ) {

        val (title, subTitle, go) = createRefs()

        Text(
            text = "$count Active polls",
            color = textColor,
            style = textStyle.tiltFont(),
            modifier = Modifier
                .constrainAs(title){
                    start.linkTo(parent.start, 6.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(subTitle.top, 6.dp)
                }
        )

        Text(
            text = "Show Details",
            color = textColor.copy(alpha = 0.7f),
            style = MaterialTheme.typography.titleMedium.tiltFont(),
            modifier = Modifier
                .constrainAs(subTitle){
                    start.linkTo(parent.start, 6.dp)
                    top.linkTo(title.bottom, 6.dp)
                    bottom.linkTo(parent.bottom, 6.dp)
                }
        )

        IconButton(
            onClick = onClick,
            modifier = Modifier.constrainAs(go){
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "",
                modifier = Modifier.size(50.dp)
                    .padding(8.dp)
            )
        }

    }

}