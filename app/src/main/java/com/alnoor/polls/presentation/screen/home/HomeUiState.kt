package com.alnoor.polls.presentation.screen.home

import com.alnoor.polls.domain.model.PollWrapper
import com.alnoor.polls.domain.model.User

data class HomeUiState(
    val user: User? = null,
    val activePolls: Int = 0,
    val recentPoll: List<PollWrapper> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null
)
