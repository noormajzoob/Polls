package com.alnoor.polls.presentation.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alnoor.polls.domain.repesitory.UserRepository
import com.alnoor.polls.domain.util.Resource
import com.alnoor.polls.presentation.commons.EmailValidator
import com.alnoor.polls.presentation.commons.PasswordValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private var _uiState by mutableStateOf(LoginScreenState())
    val uiState get() = _uiState

    fun setEmail(email: String){
        _uiState = _uiState.copy(
            email = email
        )
    }

    fun setPassword(pass: String){
        _uiState = _uiState.copy(
            password = pass
        )
    }

    fun login(){

        if (!isValidEmail(_uiState.email)) {
            _uiState = _uiState.copy(
                error = "Invalid email address"
            )
            return
        }

        if (!isValidPassword(_uiState.password)) {
            _uiState = _uiState.copy(
                error = "Password length should be more than 5"
            )
            return
        }

        _uiState = _uiState.copy(
            loading = true,
            error = null
        )

        viewModelScope.launch {
            _uiState = when(val resource = userRepository.login(_uiState.email, _uiState.password)){
                is Resource.Error -> uiState.copy(
                    loading = false,
                    error = resource.message,
                )
                is Resource.Failure -> uiState.copy(
                    loading = false,
                    error = resource.e.message,
                )
                is Resource.Success -> uiState.copy(
                    loading = false,
                    loginSuccess = true,
                    error = resource.data.token
                )
            }

        }
    }

    private fun isValidEmail(email: String) = EmailValidator.validate(email)
    private fun isValidPassword(pass: String) = PasswordValidator.validate(pass)

}