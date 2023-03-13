package com.alnoor.polls.presentation.screen.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alnoor.polls.domain.repesitory.UserRepository
import com.alnoor.polls.domain.util.Resource
import com.alnoor.polls.presentation.commons.EmailValidator
import com.alnoor.polls.presentation.commons.PasswordValidator
import com.alnoor.polls.presentation.commons.UserNameValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private var _uiState by mutableStateOf(SignupUiState())
    val uiState get() = _uiState

    fun setUserName(userName: String){
        _uiState = uiState.copy(
            userName = userName
        )
    }

    fun setEmail(email: String){
        _uiState = uiState.copy(
            email = email
        )
    }

    fun setPassword(password: String){
        _uiState = uiState.copy(
            password = password
        )
    }

    fun signup(){
        if (!isValidUserName(uiState.userName)) {
            _uiState = uiState.copy(
                error = "User name should be more than 5"
            )
            return
        }

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
            _uiState = when(val resource = userRepository.signUp(_uiState.email, _uiState.password, _uiState.userName)){
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
                    signupSuccess = true,
                    error = resource.data.token
                )
            }
        }

    }

    private fun isValidEmail(email: String) = EmailValidator.validate(email)
    private fun isValidPassword(pass: String) = PasswordValidator.validate(pass)
    private fun isValidUserName(name: String) = UserNameValidator.validate(name)

}