package com.example.noteapp.presentation.register

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.repository.AuthRepository
import com.example.noteapp.domain.ui.RegistrationUIEvent
import com.example.noteapp.domain.validators.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: AuthRepository,
) : ViewModel() {

    private val TAG = RegistrationViewModel::class.simpleName

    var registrationUIState by mutableStateOf(RegistrationUIState())

    var registrationInProgress by mutableStateOf(false)

    var incorrectReg by mutableStateOf(false)

    var allValidationPassed by mutableStateOf(false)

    private val validationScope = CoroutineScope(Dispatchers.Default)


    fun onEvent(event: RegistrationUIEvent) {
        validationScope.launch {
            validateDataWithRules()
        }
        when (event) {
            is RegistrationUIEvent.NameChange -> {
                registrationUIState = registrationUIState.copy(
                    name = event.name
                )
            }

            is RegistrationUIEvent.EmailChange -> {
                registrationUIState = registrationUIState.copy(
                    email = event.email
                )
            }

            is RegistrationUIEvent.PasswordChange -> {
                registrationUIState = registrationUIState.copy(
                    password = event.password
                )
            }
        }
    }

    private suspend fun validateDataWithRules() = withContext(Dispatchers.Default) {
        val nameResult = Validator.validateName(
            name = registrationUIState.name
        )
        val emailResult = Validator.validateEmail(
            email = registrationUIState.email
        )
        val passwordResult = Validator.validatePassword(
            password = registrationUIState.password
        )

        registrationUIState = registrationUIState.copy(
            nameError = nameResult.status,
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )

        allValidationPassed = nameResult.status && emailResult.status && passwordResult.status

    }


     fun createAccount(onComplete: () -> Unit) = viewModelScope.launch {
        try {
            registrationInProgress = true
            repository.createUser(
                registrationUIState.email,
                registrationUIState.password
            ) {isSuccessful ->
                if (isSuccessful) {
                    onComplete.invoke()
                    incorrectReg = false
                    registrationUIState = registrationUIState.copy(
                        name = "", email = "", password = ""
                    )
                    allValidationPassed = false
                } else {
                    incorrectReg = true
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "Error inside RegistrationViewModel: $e")
        } finally {
            registrationInProgress = false
        }
    }
}