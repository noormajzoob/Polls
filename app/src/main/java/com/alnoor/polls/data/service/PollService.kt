package com.alnoor.polls.data.service

import com.alnoor.polls.data.dto.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PollService {

    @GET("/vote/{id}")
    suspend fun getPollById(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<PollWrapperDto>

    @GET("/vote/user/{id}/list")
    suspend fun getUserPolls(
        @Header("Authorization") token: String,
        @Path("id") id: Long,
        @Query("offset") offset: Long = 0,
        @Query("limit") limit: Int = Int.MAX_VALUE
    ): Response<PagingResponse<PollWrapperDto>>

    @GET("/vote/user/{id}/active/list")
    suspend fun getUserActivePolls(
        @Header("Authorization") token: String,
        @Path("id") id: Long,
        @Query("offset") offset: Long = 0,
        @Query("limit") limit: Int = Int.MAX_VALUE
    ): Response<PagingResponse<PollWrapperDto>>

    @GET("/vote/user/{id}/active/count")
    suspend fun getUserActivePollsCount(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<Int>

    @POST("/vote/")
    suspend fun postPoll(
        @Header("Authorization") token: String,
        @Body vote: PollWrapperDto
    ): Response<PollWrapperDto>

    @DELETE("/vote/{id}")
    suspend fun deletePoll(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<ActivateDto>

    @PUT("/vote/{id}")
    suspend fun updatePoll(
        @Header("Authorization") token: String,
        @Path("id") id: Long,
        @Body poll: PollDto
    ): Response<ActivateDto>

    @PUT("/vote/{id}/active")
    suspend fun activatePoll(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<ActivateDto>

    @PUT("/vote/{id}/inactive")
    suspend fun deactivatePoll(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<ActivateDto>

    @GET("/vote/{id}/count")
    suspend fun getPollVotesCount(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<Int>

    @POST("/vote/selection")
    suspend fun selectVote(
        @Header("Authorization") token: String,
        @Body selection: SelectedVoteDto
    ): Response<SelectedVoteDto>

    @GET("/vote/selection/{id}")
    suspend fun getPollSelections(
        @Header("Authorization") token: String,
        @Path("id") id: Long,
        @Query("offset") offset: Long = 0,
        @Query("limit") limit: Int = Int.MAX_VALUE
    ): Response<PagingResponse<PollSelectionWrapperDto>>

    @GET("/vote")
    suspend fun getPollByUrl(
        @Header("Authorization") token: String,
        @Query("id") uuid: String
    ): Response<PollWrapperDto>

}