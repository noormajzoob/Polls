package com.alnoor.polls.presentation.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.alnoor.polls.ui.theme.tiltFont
import com.alnoor.polls.R
import com.alnoor.polls.domain.model.PollWrapper

@Composable
fun PollItemView(
    modifier: Modifier = Modifier,
    wrapper: PollWrapper,
    backgroundColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    textColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    onclick: (PollWrapper)-> Unit
) {

    ConstraintLayout(
        modifier = modifier
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(color = backgroundColor)
            .clickable { onclick(wrapper) }
            .padding(16.dp)
    ) {

        val (title, views, time, duration) = createRefs()
        val centerVert = createGuidelineFromStart(0.5f)

        Text(
            text = wrapper.poll.title,
            style = MaterialTheme.typography.headlineSmall.tiltFont(),
            color = textColor,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(title){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(views.start, 6.dp)

                width = Dimension.fillToConstraints
            }
        )

        Text(
            text = "active for ${wrapper.poll.duration}",
            style = MaterialTheme.typography.labelMedium.tiltFont(),
            color = textColor.copy(alpha = 0.7f),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(duration){
                top.linkTo(title.bottom, 6.dp)
                start.linkTo(centerVert)
            }
        )

        Text(
            text = "posted ${wrapper.poll.timestamp} ago",
            style = MaterialTheme.typography.labelMedium.tiltFont(),
            color = textColor.copy(alpha = 0.7f),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(time){
                top.linkTo(title.bottom, 6.dp)
                end.linkTo(centerVert, 6.dp)
                start.linkTo(parent.start)

                width = Dimension.fillToConstraints
            }
        )

        Row(
            modifier = Modifier.constrainAs(views){
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.views),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .padding(6.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = wrapper.poll.views.toString(),
                style = MaterialTheme.typography.labelMedium.tiltFont(),
            )
        }

    }

}