package com.alnoor.polls.presentation.screen.create_poll

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun ChoosesList(
    modifier: Modifier = Modifier,
    data: List<ChooseEntry>,
    onChooseValueChange: (ChooseEntry, Int)-> Unit,
    onChooseEditDone: (Int)-> Unit,
    onSetChooseAsEdited: (Int)-> Unit
) {

    LazyColumn(
        modifier = modifier
    ){
        items(
            count = data.size
        ){ index ->
            ChooseEditor(
                chooseEntry = data[index],
                onValueChange = {
                    onChooseValueChange(data[index].copy(value = it), index)
                },
                onDone = { edited ->
                    if (edited) onChooseEditDone(index)
                    else onSetChooseAsEdited(index)
                }
            )
        }
    }

}