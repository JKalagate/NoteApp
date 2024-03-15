package com.example.noteapp.domain.ui


sealed class LoginUIEvent {

    data class EmailChange(val email: String) : LoginUIEvent()
    data class PasswordChange(val password: String) : LoginUIEvent()


}