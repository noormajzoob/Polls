package com.alnoor.polls.presentation.screen.create_poll

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alnoor.polls.R
import com.alnoor.polls.ui.theme.tiltFont

@Composable
fun DurationEditor(
    modifier: Modifier = Modifier,
    value: Int,
    onValueChange: (Int)-> Unit,
    radius: Dp = 25.dp,
    color: Color = MaterialTheme.colorScheme.onTertiaryContainer,
    backgroundColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
    fontSize: TextUnit = 16.sp
) {

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(radius))
            .background(backgroundColor)
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(
            onClick = { onValueChange(value.dec()) },
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.minus),
                contentDescription = "",
                modifier = Modifier
                    .size(50.dp)
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "$value",
            style = MaterialTheme.typography.headlineSmall.tiltFont(),
            color = color,
            fontSize = fontSize
        )
        Spacer(modifier = Modifier.width(6.dp))

        IconButton(
            onClick = { onValueChange(value.inc()) },
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.add),
                contentDescription = "",
                modifier = Modifier
                    .size(50.dp)
                    .padding(8.dp)
            )
        }
    }

}