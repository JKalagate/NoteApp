package com.example.noteapp.domain.validators

import java.util.regex.Pattern

object Validator {

    fun validateName(name:String): ValidationResult {
        //Min. 4 characters, including letters and digits."
        val regex = "^([a-zA-Z0-9]*).{4,}$"
        val pattern = Pattern.compile(regex)
        return ValidationResult(
            !name.isNullOrEmpty() && pattern.matcher(name).matches()
        )
    }

    fun validateEmail(email:String): ValidationResult {
        //Incorrect email format
        val regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"
        val pattern = Pattern.compile(regex)
        return ValidationResult(
            (!email.isNullOrEmpty() && pattern.matcher(email).matches())
        )
    }

    fun validatePassword(password:String): ValidationResult {
        //At least 6 characters with at least one digit and one special character.
        val regex = "^(?=.*\\d)(?=.*[!@#\$%^&*(),.?\":{}|<>])(?=.*[a-zA-Z]).{6,}\$"
        val pattern = Pattern.compile(regex)
        return ValidationResult(
            !password.isNullOrEmpty() && pattern.matcher(password).matches()
        )
    }


}

data class ValidationResult(
    val status: Boolean = false
)