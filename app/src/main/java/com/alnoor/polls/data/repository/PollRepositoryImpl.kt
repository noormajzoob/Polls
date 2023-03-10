package com.alnoor.polls.data.repository

import com.alnoor.polls.data.dto.PollSelectionWrapperDto
import com.alnoor.polls.data.dto.SelectedVoteDto
import com.alnoor.polls.data.mapper.PollSelectionMapper
import com.alnoor.polls.data.mapper.PollWrapperMapper
import com.alnoor.polls.data.preference.UserPreferenceMapper
import com.alnoor.polls.data.preference.UserPreferenceStore
import com.alnoor.polls.data.service.PollService
import com.alnoor.polls.data.service.UserService
import com.alnoor.polls.domain.model.PollSelection
import com.alnoor.polls.domain.model.PollWrapper
import com.alnoor.polls.domain.repesitory.PollRepository
import com.alnoor.polls.domain.repesitory.UserRepository
import com.alnoor.polls.domain.util.Resource
import com.alnoor.polls.domain.util.UserNotLoginException
import com.alnoor.polls.util.Constant
import javax.inject.Inject

class PollRepositoryImpl @Inject constructor(
    private val pollService: PollService,
    private val userRepository: UserRepository,
    private val userPreferenceStore: UserPreferenceStore,
    private val userPreferenceMapper: UserPreferenceMapper,
    private val pollWrapperMapper: PollWrapperMapper,
    private val pollSelectionMapper: PollSelectionMapper
): PollRepository {

    override suspend fun getPoll(pollId: Long): Resource<PollWrapper> {
        try {
            if (!userPreferenceStore.isLogged())
                return Resource.Error("User not logged!")

            userPreferenceStore.getPrimitive(Constant.TOKEN_PREF_KEY)?.let { token ->
                val response = pollService.getPollById("Bearer $token", pollId)
                if (response.isSuccessful){
                    return Resource.Success(pollWrapperMapper.mapFrom(response.body()!!))
                }else{
                    if (response.code() == 401){
                        userRepository.regenerateToken()
                        return getPoll(pollId)
                    }else{
                        return Resource.Error("Poll not found!")
                    }
                }

            }?: return Resource.Error("token not found!")
        }catch (e: Exception){
            e.printStackTrace()
            return Resource.Failure(e)
        }
    }

    override suspend fun getPollVotes(pollId: Long): Resource<List<PollSelection>> {
        try {
            if (!userPreferenceStore.isLogged())
                return Resource.Error("User not logged!")

            userPreferenceStore.getPrimitive(Constant.TOKEN_PREF_KEY)?.let { token ->
                val response = pollService.getPollSelections("Bearer $token", pollId)

                if (response.isSuccessful){
                    return Resource.Success(response.body()!!.data.map {
                        pollSelectionMapper.mapTo(it)
                    })
                }else{
                    if (response.code() == 401){
                        userRepository.regenerateToken()
                        return getPollVotes(pollId)
                    }else{
                        return Resource.Error("Poll not found!")
                    }
                }
            }?: return Resource.Error("Poll not found!")

        }catch (e: Exception){
            e.printStackTrace()
            return Resource.Failure(e)
        }
    }

    override suspend fun getPollByUrl(urlId: String): Resource<PollWrapper> {
        try {
            if (!userPreferenceStore.isLogged())
                return Resource.Error("User not logged!")

            userPreferenceStore.getPrimitive(Constant.TOKEN_PREF_KEY)?.let { token ->
                val response = pollService.getPollByUrl("Bearer $token", urlId)

                if (response.isSuccessful){
                    return Resource.Success(pollWrapperMapper.mapFrom(response.body()!!))
                }else{
                    if (response.code() == 401){
                        userRepository.regenerateToken()
                        return getPollByUrl(urlId)
                    }else{
                        return Resource.Error("Poll not found!")
                    }
                }
            }?: return Resource.Error("Poll not found!")
        }catch (e: Exception){
            e.printStackTrace()
            return Resource.Failure(e)
        }
    }

    override suspend fun getUserActivePolls(userId: Long): Resource<List<PollWrapper>> {
        try {
            if (!userPreferenceStore.isLogged())
                return Resource.Error("User not logged!")

            userPreferenceStore.getPrimitive(Constant.TOKEN_PREF_KEY)?.let { token ->
                val response = pollService.getUserActivePolls("Bearer $token", userId)

                if (response.isSuccessful){
                    return Resource.Success(response.body()!!.data.map {
                        pollWrapperMapper.mapFrom(it)
                    })
                }else{
                    if (response.code() == 401){
                        userRepository.regenerateToken()
                        return getUserPolls(userId)
                    }else{
                        return Resource.Error("something went wrong!")
                    }
                }
            }?: return Resource.Error("something went wrong!")
        }catch (e: Exception){
            e.printStackTrace()
            return Resource.Failure(e)
        }
    }

    override suspend fun getUserPolls(userId: Long): Resource<List<PollWrapper>> {
        try {
            if (!userPreferenceStore.isLogged())
                return Resource.Error("User not logged!")

            userPreferenceStore.getPrimitive(Constant.TOKEN_PREF_KEY)?.let { token ->
                val response = pollService.getUserPolls("Bearer $token", userId)

                if (response.isSuccessful){
                    return Resource.Success(response.body()!!.data.map {
                        pollWrapperMapper.mapFrom(it)
                    })
                }else{
                    if (response.code() == 401){
                        userRepository.regenerateToken()
                        return getUserPolls(userId)
                    }else{
                        return Resource.Error("something went wrong!")
                    }
                }
            }?: return Resource.Error("something went wrong!")
        }catch (e: Exception){
            e.printStackTrace()
            return Resource.Failure(e)
        }
    }

    override suspend fun getActivePollsCount(userId: Long): Resource<Int> {
        try {
            if (!userPreferenceStore.isLogged())
                return Resource.Error("User not logged!")

            userPreferenceStore.getPrimitive(Constant.TOKEN_PREF_KEY)?.let { token ->
                val response = pollService.getUserActivePollsCount("Bearer $token", userId)

                if (response.isSuccessful){
                    return Resource.Success(response.body()!!)
                }else{
                    if (response.code() == 401){
                        userRepository.regenerateToken()
                        return getActivePollsCount(userId)
                    }else{
                        return Resource.Error("user not found")
                    }
                }
            }?: return Resource.Error("user not found")
        }catch (e: Exception){
            e.printStackTrace()
            return Resource.Failure(e)
        }
    }

    override suspend fun getPollVotesCount(pollId: Long): Resource<Int> {
        try {
            if (!userPreferenceStore.isLogged())
                return Resource.Error("User not logged!")

            userPreferenceStore.getPrimitive(Constant.TOKEN_PREF_KEY)?.let { token ->
                val response = pollService.getPollVotesCount("Bearer $token", pollId)

                if (response.isSuccessful){
                    return Resource.Success(response.body()!!)
                }else{
                    if (response.code() == 401){
                        userRepository.regenerateToken()
                        return getPollVotesCount(pollId)
                    }else{
                        return Resource.Error("poll not found")
                    }
                }
            }?: return Resource.Error("poll not found")
        }catch (e: Exception){
            e.printStackTrace()
            return Resource.Failure(e)
        }
    }

    override suspend fun postPoll(poll: PollWrapper): Resource<PollWrapper> {
        try {
            if (!userPreferenceStore.isLogged())
                return Resource.Error("User not logged!")

            userPreferenceStore.getPrimitive(Constant.TOKEN_PREF_KEY)?.let { token ->
                val response = pollService.postPoll("Bearer $token", pollWrapperMapper.mapTo(poll))

                if (response.isSuccessful){
                    return Resource.Success(pollWrapperMapper.mapFrom(response.body()!!))
                }else{
                    if (response.code() == 401){
                        userRepository.regenerateToken()
                        return postPoll(poll)
                    }else{
                        return Resource.Error("something went wrong")
                    }
                }
            }?: return Resource.Error("something went wrong")

        }catch (e: Exception){
            e.printStackTrace()
            return Resource.Failure(e)
        }
    }

    override suspend fun postVote(userId: Long, chooseId: Long): Resource<String> {
        try {
            if (!userPreferenceStore.isLogged())
                return Resource.Error("User not logged!")

            userPreferenceStore.getPrimitive(Constant.TOKEN_PREF_KEY)?.let { token ->
                val response = pollService.selectVote("Bearer $token", SelectedVoteDto(
                    choose = chooseId,
                    user = userId
                ))

                if (response.isSuccessful){
                    return Resource.Success("Vote has been selected!")
                }else{
                    if (response.code() == 401){
                        userRepository.regenerateToken()
                        return postVote(userId, chooseId)
                    }else{
                        return Resource.Error("something went wrong")
                    }
                }
            }?: return Resource.Error("something went wrong")

        }catch (e: Exception){
            e.printStackTrace()
            return Resource.Failure(e)
        }
    }

    override suspend fun activePoll(pollId: Long): Resource<String> {
        try {
            if (!userPreferenceStore.isLogged())
                return Resource.Error("User not logged!")

            userPreferenceStore.getPrimitive(Constant.TOKEN_PREF_KEY)?.let { token ->
                val response = pollService.activatePoll("Bearer $token", pollId)

                if (response.isSuccessful){
                    return Resource.Success("vote has been activated")
                }else{
                    if (response.code() == 401){
                        userRepository.regenerateToken()
                        return activePoll(pollId)
                    }else{
                        return Resource.Error("poll not found")
                    }
                }
            }?: return Resource.Error("poll not found")
        }catch (e: Exception){
            e.printStackTrace()
            return Resource.Failure(e)
        }
    }

    override suspend fun deactivatePoll(pollId: Long): Resource<String> {
        try {
            if (!userPreferenceStore.isLogged())
                return Resource.Error("User not logged!")

            userPreferenceStore.getPrimitive(Constant.TOKEN_PREF_KEY)?.let { token ->
                val response = pollService.deactivatePoll("Bearer $token", pollId)

                if (response.isSuccessful){
                    return Resource.Success("vote has been deactivated")
                }else{
                    if (response.code() == 401){
                        userRepository.regenerateToken()
                        return deactivatePoll(pollId)
                    }else{
                        return Resource.Error("poll not found")
                    }
                }
            }?: return Resource.Error("poll not found")
        }catch (e: Exception){
            e.printStackTrace()
            return Resource.Failure(e)
        }
    }
}