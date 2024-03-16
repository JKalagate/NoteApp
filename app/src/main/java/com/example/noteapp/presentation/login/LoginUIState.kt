package com.example.noteapp.presentation.login

data class LoginUIState (
    val email: String = "",
    val password: String = "",

    val emailError: Boolean = false,
    val passwordError: Boolean = false

)