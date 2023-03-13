package com.alnoor.polls.presentation.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alnoor.polls.domain.model.Poll
import com.alnoor.polls.domain.model.PollWrapper
import com.alnoor.polls.ui.theme.tiltFont

@Composable
fun RecentPolls(
    modifier: Modifier = Modifier,
    data: List<PollWrapper>,
    pollView: @Composable (PollWrapper)-> Unit
) {
    LazyColumn(
        modifier = modifier
    ){

        if (data.isEmpty()){
            item{
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(
                        text = "There is no recent polls",
                        style = MaterialTheme.typography.headlineSmall.tiltFont()
                    )
                }
            }
        }else{
            items(
                count = data.size,
                key = { index ->
                    data[index].poll.id
                }
            ){ index ->
                pollView(data[index])
            }
        }
    }
}