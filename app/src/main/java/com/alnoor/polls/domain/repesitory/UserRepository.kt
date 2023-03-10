package com.alnoor.polls.domain.repesitory

import com.alnoor.polls.domain.model.LoginResponse
import com.alnoor.polls.domain.model.User
import com.alnoor.polls.domain.util.Resource

interface UserRepository {

    suspend fun login(email: String, password: String): Resource<LoginResponse>
    suspend fun signUp(email: String, password: String, userName: String): Resource<LoginResponse>
    suspend fun regenerateToken()
}