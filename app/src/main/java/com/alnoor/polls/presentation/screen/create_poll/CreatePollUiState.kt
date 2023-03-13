package com.alnoor.polls.presentation.screen.create_poll

data class CreatePollUiState(
    val pollTitle: String = "",
    val pollDuration: Int = 0,
    val pollChooses: List<ChooseEntry> = listOf(ChooseEntry()),
    val loading: Boolean = false,
    val error: String? = null,
    val postPollSuccess: Boolean = false,
    val pollId: String? = null
)


data class ChooseEntry(
    val value: String = "",
    val edited: Boolean = false,
)