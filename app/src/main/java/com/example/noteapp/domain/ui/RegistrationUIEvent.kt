package com.example.noteapp.domain.ui

sealed class RegistrationUIEvent {

    data class NameChange(val name: String) : RegistrationUIEvent()
    data class EmailChange(val email: String) : RegistrationUIEvent()
    data class PasswordChange(val password: String) : RegistrationUIEvent()

}