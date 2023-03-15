package com.alnoor.polls.presentation.screen.vote

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alnoor.polls.domain.model.PollChoose
import com.alnoor.polls.domain.repesitory.PollRepository
import com.alnoor.polls.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VoteViewModel @Inject constructor(
    private val pollRepository: PollRepository
): ViewModel(){

    private var _uiState by mutableStateOf(VoteUiState())
    val uiState get() = _uiState

    fun getPoll(id: String){
        _uiState = uiState.copy(
            loading = true
        )

        viewModelScope.launch {
            fetchPoll(id)
        }
    }

    fun setChoose(choose: PollChoose){
        _uiState = uiState.copy(
            selectedChoose = choose
        )
    }

    private suspend fun fetchPoll(id: String){
        val res = pollRepository.getPollByUrl(id)

        _uiState = when(res){
            is Resource.Error -> uiState.copy(
                loading = false,
                pollUrl = id,
                error = res.message
            )
            is Resource.Failure -> uiState.copy(
                loading = false,
                pollUrl = id,
                error = res.e.message
            )
            is Resource.Success -> {
                Log.d("TAG", "fetchPoll: ${res.data}")
                uiState.copy(
                    loading = false,
                    pollUrl = id,
                    poll = res.data
                )
            }
        }
    }

    fun postVote(){
        if (uiState.selectedChoose == null){
            _uiState = uiState.copy(
                submitMsg = "Please select vote"
            )

            return
        }

        _uiState = uiState.copy(
            submitLoading = true
        )

        viewModelScope.launch {
            val res = pollRepository.postVote(uiState.selectedChoose?.id!!)

            _uiState = when(res){
                is Resource.Error -> uiState.copy(
                    submitLoading = false,
                    submitMsg = res.message
                )
                is Resource.Failure -> uiState.copy(
                    submitLoading = false,
                    submitMsg = res.e.message
                )
                is Resource.Success -> uiState.copy(
                    submitLoading = false,
                    submitMsg = "Vote has been submitted"
                )
            }
        }
    }

    fun refresh(){
        if (uiState.pollUrl.isEmpty()){
            _uiState = uiState.copy(
                error = "Invalid url"
            )

            return
        }

        _uiState = uiState.copy(
            loading = true
        )

        viewModelScope.launch {
            fetchPoll(uiState.pollUrl)
        }
    }

}