package com.alnoor.polls.presentation.screen.create_poll

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alnoor.polls.domain.repesitory.PollRepository
import com.alnoor.polls.domain.util.Resource
import com.alnoor.polls.presentation.commons.PollValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePollViewModel @Inject constructor(
    private val pollRepository: PollRepository
): ViewModel(){

    private var _uiState by mutableStateOf(CreatePollUiState())
    val uiState get() = _uiState

    fun setPollTitle(title: String){
        _uiState = uiState.copy(
            pollTitle = title
        )
    }

    fun setPollDuration(duration: Int){
        if (duration < 0) return

        _uiState = uiState.copy(
            pollDuration = duration
        )
    }

    fun setChose(chooseEntry: ChooseEntry, index: Int){
        _uiState = uiState.copy(
            pollChooses = uiState.pollChooses.toMutableList().apply {
                this[index] = chooseEntry
            }
        )
    }

    fun setChooseAsEdited(index: Int){
        _uiState = uiState.copy(
            pollChooses = uiState.pollChooses.toMutableList().apply {
                this[index] = this[index].copy(
                    edited = true
                )
                if (index == this.size - 1 && this.size <= 5) add(ChooseEntry())
            }
        )
    }

    fun setChooseAsEditable(index: Int){
        _uiState = uiState.copy(
            pollChooses = uiState.pollChooses.toMutableList().apply {
                this[index] = this[index].copy(
                    edited = false
                )
            }
        )
    }

    fun submitPoll(){
        if (!PollValidator.validateTitle(uiState.pollTitle)){
            _uiState = uiState.copy(
                error = "Poll title should be with length between 6 and 500"
            )

            return
        }

        if (!uiState.pollChooses.all { PollValidator.validateChoose(it.value) }){
            _uiState = uiState.copy(
                error = "Poll should be with length between 6 and 250"
            )

            return
        }

        if (!PollValidator.validateChooseCount(uiState.pollChooses.size)){
            _uiState = uiState.copy(
                error = "Poll chooses should be at least 1 and at most 5"
            )

            return
        }

        _uiState = uiState.copy(
            loading = true,
            error = null
        )

        viewModelScope.launch {
            val resource = pollRepository.postPoll(
                title = uiState.pollTitle,
                duration = uiState.pollDuration,
                chooses = uiState.pollChooses.map { it.value }
            )

            _uiState = when(resource){
                is Resource.Error -> uiState.copy(
                    error = resource.message,
                    loading = false
                )
                is Resource.Failure -> uiState.copy(
                    error = resource.e.message,
                    loading = false
                )
                is Resource.Success -> uiState.copy(
                    postPollSuccess = true,
                    loading = false,
                    pollId = resource.data.poll.urlId
                )
            }
        }
    }

}