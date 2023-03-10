package com.alnoor.polls.data.dto

import com.google.gson.annotations.SerializedName

data class PollChooseDto(
    val id: Long? = null,
    @SerializedName("vote")
    val pollId: Long? = null,
    val content: String? = null
)
