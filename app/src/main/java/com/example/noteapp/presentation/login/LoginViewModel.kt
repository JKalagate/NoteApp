package com.example.noteapp.presentation.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.repository.AuthRepository
import com.example.noteapp.domain.ui.LoginUIEvent
import com.example.noteapp.domain.validators.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
) : ViewModel() {

    private val TAG = LoginViewModel::class.simpleName

    var loginUiState by mutableStateOf(LoginUIState())

    var loginInProgress by mutableStateOf(false)

    var incorrect by mutableStateOf(false)

    var allValidationPassed by mutableStateOf(false)

    private val validationScope = CoroutineScope(Dispatchers.Default)

    fun onEvent(event: LoginUIEvent) {
        validationScope.launch {
            validateDataWithRules()
        }
        when (event) {
            is LoginUIEvent.EmailChange -> {
                loginUiState = loginUiState.copy(
                    email = event.email
                )
            }

            is LoginUIEvent.PasswordChange -> {
                loginUiState = loginUiState.copy(
                    password = event.password
                )
            }
        }
    }

    private suspend fun validateDataWithRules() = withContext(Dispatchers.Default) {

        val emailResult = Validator.validateEmail(
            email = loginUiState.email
        )
        val passwordResult = Validator.validatePassword(
            password = loginUiState.password
        )

        loginUiState = loginUiState.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )

        allValidationPassed = emailResult.status && passwordResult.status




    }




    fun login(onComplete: () -> Unit) = viewModelScope.launch {
        try {
            loginInProgress = true
            repository.login(
                loginUiState.email,
                loginUiState.password
            ) { isSuccessful ->
                if (isSuccessful) {
                    onComplete.invoke()
                    incorrect = false
                    loginUiState = loginUiState.copy(email = "", password = "")
                    allValidationPassed = false
                } else {
                    incorrect = true
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "Error inside LoginViewModel: $e")
        } finally {
            loginInProgress = false
        }

    }
}