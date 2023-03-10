package com.alnoor.polls.domain.model

data class Poll(
    val id: Long,
    val title: String,
    val user: Long,
    val timestamp: String,
    val duration: String,
    val views: Int,
    val urlId: String,
    val status: Boolean
)
