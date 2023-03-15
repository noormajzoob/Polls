package com.alnoor.polls.presentation.screen.view_poll

import com.alnoor.polls.domain.model.PollSelection
import com.alnoor.polls.domain.model.PollWrapper

data class ViewPollUiState(
    val poll: PollWrapper? = null,
    val votes: List<PollSelection> = emptyList(),
    val error: String? = null,
    val errorType: ErrorType? = null,
    val loading: Boolean = true
)

sealed class ErrorType{
    object ParseError: ErrorType()
    object NetworkError: ErrorType()
}
