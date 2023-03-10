package com.alnoor.polls.domain.model

data class LoginResponse(
    val user: User,
    val token: String
)
