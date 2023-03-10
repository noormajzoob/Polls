package com.alnoor.polls.domain.model

data class PollSelection(
    val user: User,
    val choose: PollChoose,
)
