package com.example.noteapp.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.repository.AuthRepository
import com.example.noteapp.domain.ui.RegistrationUIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: AuthRepository,
) : ViewModel() {

    var registrationUiState by mutableStateOf(RegistrationUIState())

     var registrationInProgress by mutableStateOf(false)


    fun onEvent(event: RegistrationUIEvent) {
        when (event) {
            is RegistrationUIEvent.NameChange -> {
                registrationUiState = registrationUiState.copy(
                    name = event.name
                )
            }

            is RegistrationUIEvent.EmailChange -> {
                registrationUiState = registrationUiState.copy(
                    email = event.email
                )
            }

            is RegistrationUIEvent.PasswordChange -> {
                registrationUiState = registrationUiState.copy(
                    password = event.password
                )
            }

            is RegistrationUIEvent.RegistrationButtonClicked -> {
                createAccount()
            }
        }
    }

    private fun createAccount() = viewModelScope.launch {
        registrationInProgress = true
        repository.createUser(
            registrationUiState.email,
            registrationUiState.password
        )
        registrationInProgress = false
    }
}