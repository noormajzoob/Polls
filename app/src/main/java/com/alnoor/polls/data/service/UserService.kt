package com.alnoor.polls.data.service

import com.alnoor.polls.data.dto.LoginRepoDto
import com.alnoor.polls.data.dto.UserDto
import com.alnoor.polls.domain.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {

    @POST("/user/")
    suspend fun signUp(
        @Body user: UserDto
    ): Response<UserDto>

    @POST("/user/login")
    suspend fun login(
        @Body user: UserDto
    ): Response<LoginRepoDto>

    @GET("/user/{id}")
    suspend fun getUser(
        @Header("Authorization : Bearer ") token: String,
        @Path("id") id: Long
    ): Response<UserDto>

}