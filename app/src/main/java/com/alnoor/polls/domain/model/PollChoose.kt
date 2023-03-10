package com.alnoor.polls.domain.model

data class PollChoose(
    val id: Long,
    val pollId: Long,
    val content: String
)
