package com.alnoor.polls.presentation.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alnoor.polls.data.mapper.UserEntityMapper
import com.alnoor.polls.data.preference.UserPreferenceMapper
import com.alnoor.polls.data.preference.UserPreferenceStore
import com.alnoor.polls.domain.model.User
import com.alnoor.polls.domain.repesitory.PollRepository
import com.alnoor.polls.domain.util.Resource
import com.alnoor.polls.util.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userPreferenceStore: UserPreferenceStore,
    private val mapper: UserPreferenceMapper,
    private val userEntityMapper: UserEntityMapper,
    private val pollRepository: PollRepository
): ViewModel() {

    private var _uiState by mutableStateOf(HomeUiState())
    val uiState get() = _uiState

    init {
        _uiState = uiState.copy(
            loading = true
        )
        viewModelScope.launch {
            userPreferenceStore.getObject(Constant.USER_PREFS_KEY, mapper)?.let { userEntity ->
                val user = userEntityMapper.mapTo(userEntity)
                _uiState = uiState.copy(
                    user = user
                )

                getActivePollsCount(user)
                getRecentPolls(user)
            }
        }
    }

    private suspend fun getActivePollsCount(user: User){
        val resource = pollRepository.getActivePollsCount(user.id)

        _uiState = when(resource){
            is Resource.Error -> uiState.copy(
                error = resource.message
            )
            is Resource.Failure -> uiState.copy(
                error = resource.e.message
            )
            is Resource.Success -> uiState.copy(
                activePolls = resource.data
            )
        }
    }

    private suspend fun getRecentPolls(user: User){
        val resource = pollRepository.getUserPolls(user.id, 0, 5)

        _uiState = when(resource){
            is Resource.Error ->  uiState.copy(
                error = resource.message,
                loading = false
            )
            is Resource.Failure -> uiState.copy(
                error = resource.e.message,
                loading = false
            )
            is Resource.Success -> uiState.copy(
                recentPoll = resource.data,
                loading = false
            )
        }

    }

    fun refresh(){
        _uiState = uiState.copy(
            loading = true,
            error = null
        )

        viewModelScope.launch {
            uiState.user?.let {
                getActivePollsCount(it)
                getRecentPolls(it)
            }
        }
    }

}