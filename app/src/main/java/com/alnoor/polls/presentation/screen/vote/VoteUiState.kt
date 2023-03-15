package com.alnoor.polls.presentation.screen.vote

import com.alnoor.polls.domain.model.PollChoose
import com.alnoor.polls.domain.model.PollWrapper

data class VoteUiState(
    val poll: PollWrapper? = null,
    val selectedChoose: PollChoose? = null,
    val pollUrl: String = "",
    val loading: Boolean = false,
    val submitMsg: String? = "",
    val submitLoading: Boolean = false,
    val error: String? = null
)
