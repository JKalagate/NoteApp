package com.example.noteapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noteapp.R
import com.example.noteapp.presentation.components.AppTextFieldComponent
import com.example.noteapp.presentation.components.ButtonComponents
import com.example.noteapp.presentation.components.HeadingTextComponent
import com.example.noteapp.presentation.components.NormalTextComponent
import com.example.noteapp.presentation.components.PasswordTextFieldComponent
import com.example.noteapp.presentation.components.TextComponent
import com.example.noteapp.ui.theme.BgLightBlue

@Composable
fun RegistrationScreen() {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(BgLightBlue)
            .padding(top = 120.dp, start = 28.dp, end = 28.dp, bottom = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BgLightBlue),
            verticalArrangement = Arrangement.spacedBy(15.dp)

        ) {
            HeadingTextComponent(value = stringResource(R.string.sign_up))
            NormalTextComponent(value = stringResource(id = R.string.continueSignUp))

            Spacer(modifier = Modifier.height(90.dp))

            AppTextFieldComponent(
                labelValue = stringResource(id = R.string.name),
                painterResource = Icons.Default.Person,
                onTextChanged = {

                }
            )
            AppTextFieldComponent(
                labelValue = stringResource(id = R.string.email),
                painterResource = Icons.Default.Email,
                onTextChanged = {

                }
            )
            PasswordTextFieldComponent(
                labelValue = stringResource(id = R.string.password) ,
                painterResource = painterResource(id = R.drawable.lock),
                onTextChanged = {
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            ButtonComponents(
                value = stringResource(id = R.string.register),
                onButtonClicked = {


                }
            )

            TextComponent(
                tryingToLogin = 1,
                onTextSelected = {

                }
            )

        }
    }

}

@Preview(
    showSystemUi = true,
    showBackground = true)
@Composable
fun RegistrationScreenPrev() {
    RegistrationScreen()

}