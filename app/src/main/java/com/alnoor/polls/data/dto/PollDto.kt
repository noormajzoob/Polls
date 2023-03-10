package com.alnoor.polls.data.dto

data class PollDto(
    val id: Long? = null,
    val title: String? = null,
    val user: Long? = null,
    val timestamp: Long? = null,
    val duration: Int? = null,
    val views: Int? = null,
    val urlId: String? = null,
    val status: Boolean? = true
)
