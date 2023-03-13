package com.alnoor.polls.presentation.screen.signup

data class SignupUiState(
    val userName: String = "",
    val email: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val error: String? = null,
    val signupSuccess: Boolean = false
)
