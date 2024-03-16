package com.example.noteapp.presentation.register

data class RegistrationUIState(
    val name: String = "",
    val email: String = "",
    val password: String = "",

    val nameError: Boolean = false,
    val emailError: Boolean = false,
    val passwordError: Boolean = false,
)