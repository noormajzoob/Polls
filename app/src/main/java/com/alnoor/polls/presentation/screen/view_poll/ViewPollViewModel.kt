package com.alnoor.polls.presentation.screen.view_poll

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alnoor.polls.domain.model.PollWrapper
import com.alnoor.polls.domain.repesitory.PollRepository
import com.alnoor.polls.domain.util.Resource
import com.alnoor.polls.presentation.commons.fromJson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewPollViewModel @Inject constructor(
    private val pollRepository: PollRepository
): ViewModel(){


    private var _uiState by mutableStateOf(ViewPollUiState())
    val uiState get() = _uiState

    fun parsePoll(pollJson: String){
        viewModelScope.launch {
            try {
                val poll = pollJson.fromJson(PollWrapper::class.java)

                getPollVotes(poll)
            }catch (e: Exception){
                _uiState = uiState.copy(
                    error = "Something went wrong!",
                    errorType = ErrorType.ParseError,
                    loading = false
                )
            }
        }
    }

    private suspend fun getPollVotes(poll: PollWrapper){
        val resource = pollRepository.getPollVotes(pollId = poll.poll.id, limit = Int.MAX_VALUE, 0)

        _uiState = when(resource){
            is Resource.Error ->  uiState.copy(
                error = resource.message,
                errorType = ErrorType.NetworkError,
                loading = false
            )
            is Resource.Failure -> uiState.copy(
                error = resource.e.message,
                errorType = ErrorType.NetworkError,
                loading = false
            )
            is Resource.Success -> uiState.copy(
                poll = poll,
                votes = resource.data,
                loading = false
            )
        }
    }

    fun refresh(){
        _uiState = uiState.copy(
            error = null,
            errorType = null
        )

        viewModelScope.launch {
            uiState.poll?.let {
                getPollVotes(it)
            }
        }
    }

}