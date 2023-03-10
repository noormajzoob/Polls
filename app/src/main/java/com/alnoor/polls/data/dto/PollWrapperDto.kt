package com.alnoor.polls.data.dto

data class PollWrapperDto(
    val vote: PollDto,
    val chooses: List<PollChooseDto>
)
