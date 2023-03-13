package com.alnoor.polls.presentation.screen.create_poll


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ChooseEditor(
    modifier: Modifier = Modifier,
    chooseEntry: ChooseEntry,
    onValueChange: (String) -> Unit,
    onDone: (Boolean)-> Unit,
) {

    Row(modifier = modifier.padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = chooseEntry.value,
            onValueChange = onValueChange,
            maxLines = 1,
            enabled = !chooseEntry.edited,
            placeholder = { Text(text = "Enter choose") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f)
        )

        Spacer(modifier = Modifier.width(6.dp))

        IconButton(
            onClick = {
                if (chooseEntry.edited) onDone(false) else onDone(true) },
        ) {
            Icon(
                imageVector = if (chooseEntry.edited) Icons.Default.Edit else Icons.Default.Done,
                contentDescription = "",
                modifier = Modifier
                    .size(50.dp)
                    .padding(8.dp)
            )
        }
    }

}