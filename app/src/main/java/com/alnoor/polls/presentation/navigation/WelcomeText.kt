package com.alnoor.polls.presentation.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import com.alnoor.polls.ui.theme.fontFamily

@Composable
fun welcomeAnnotatedString(name: String): AnnotatedString {
    return buildAnnotatedString {
        append("Welcome,\n")
        addStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onBackground,
            ),
            start = 0,
            end = this.length - 1
        )
        val len = this.length
        append(name)
        addStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.tertiary,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold
            ),
            start = len,
            end = this.length
        )
    }
}