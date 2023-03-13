package com.alnoor.polls.presentation.screen.login

data class LoginScreenState(
    val email: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val loginSuccess: Boolean = false,
    val error: String? = null
)
