package com.alnoor.polls.domain.model

data class PollWrapper(
    val poll: Poll,
    val chooses: List<PollChoose>
)
