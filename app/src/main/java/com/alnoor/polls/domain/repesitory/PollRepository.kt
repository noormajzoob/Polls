package com.alnoor.polls.domain.repesitory

import com.alnoor.polls.domain.model.PollSelection
import com.alnoor.polls.domain.model.PollWrapper
import com.alnoor.polls.domain.util.Resource

interface PollRepository {

    suspend fun getPoll(pollId: Long): Resource<PollWrapper>
    suspend fun getPollVotes(pollId: Long): Resource<List<PollSelection>>
    suspend fun getPollByUrl(urlId: String): Resource<PollWrapper>
    suspend fun getUserPolls(userId: Long): Resource<List<PollWrapper>>
    suspend fun getUserActivePolls(userId: Long): Resource<List<PollWrapper>>
    suspend fun getActivePollsCount(userId: Long): Resource<Int>
    suspend fun getPollVotesCount(pollId: Long): Resource<Int>
    suspend fun postPoll(poll: PollWrapper): Resource<PollWrapper>
    suspend fun postVote(userId: Long, chooseId: Long): Resource<String>
    suspend fun activePoll(pollId: Long): Resource<String>
    suspend fun deactivatePoll(pollId: Long): Resource<String>

}