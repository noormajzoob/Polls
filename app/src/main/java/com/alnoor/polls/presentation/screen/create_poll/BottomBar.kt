package com.alnoor.polls.presentation.screen.create_poll

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.alnoor.polls.R
import com.alnoor.polls.ui.theme.tiltFont

@Composable
fun StateView(
    modifier: Modifier = Modifier,
    message: String? = null,
    isPosted: Boolean = false,
    isLoading: Boolean = false,
    onAction: (Action)-> Unit,
    backgroundColor: Color = BottomAppBarDefaults.containerColor,
    messageTextColor: Color = MaterialTheme.colorScheme.onBackground,
) {

    ConstraintLayout(
        modifier = modifier
            .background(backgroundColor)
            .padding(16.dp)
    ) {

        val (messageRef, loading, actionBtn) = createRefs()

        if (message != null) {
            Text(
                text = message,
                style = MaterialTheme.typography.labelMedium.tiltFont(),
                color = messageTextColor,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.constrainAs(messageRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(actionBtn.start, 6.dp)
                    bottom.linkTo(parent.bottom)

                    width = Dimension.fillToConstraints
                }
            )
        }

        if (isLoading){
            CircularProgressIndicator(
                modifier = Modifier.constrainAs(loading){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }
            )
        }

        FloatingActionButton(
            onClick = {
                if (isPosted) onAction(Action.Share)
                else onAction(Action.Send)
            },
            containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
            modifier = Modifier.constrainAs(actionBtn){
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        ) {
            Icon(
                if (isPosted) ImageVector.vectorResource(id = R.drawable.share)
                else ImageVector.vectorResource(id = R.drawable.send),
                null
            )
        }
    }

}

sealed class Action{
    object Send: Action()
    object Share: Action()
}