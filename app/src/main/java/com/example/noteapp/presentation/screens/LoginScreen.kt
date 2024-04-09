package com.example.noteapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.noteapp.R
import com.example.noteapp.domain.ui.LoginUIEvent
import com.example.noteapp.navigation.Screens
import com.example.noteapp.presentation.components.AppTextFieldComponent
import com.example.noteapp.presentation.components.ButtonComponents
import com.example.noteapp.presentation.components.HeadingTextComponent
import com.example.noteapp.presentation.components.ProblemTextComponent
import com.example.noteapp.presentation.components.NormalTextComponent
import com.example.noteapp.presentation.components.PasswordTextFieldComponent
import com.example.noteapp.presentation.components.TextComponent
import com.example.noteapp.presentation.login.LoginViewModel
import com.example.noteapp.ui.theme.BgLightBlue

@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(BgLightBlue)
                .padding(top = 102.dp, start = 28.dp, end = 28.dp, bottom = 28.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BgLightBlue),
                verticalArrangement = Arrangement.spacedBy(15.dp)

            ) {
                HeadingTextComponent(value = stringResource(R.string.login_in))
                NormalTextComponent(value = stringResource(id = R.string.welcome))

                Spacer(modifier = Modifier.height(80.dp))

                if (loginViewModel.incorrect) {
                    ProblemTextComponent(value = stringResource(id = R.string.incorrect_login))
                }

                AppTextFieldComponent(
                    value = stringResource(id = R.string.email),
                    painterResource = Icons.Default.Email,
                    onTextChanged = {
                        val a = loginViewModel.onEvent(LoginUIEvent.EmailChange(it))
                    },
                    errorStatus = loginViewModel.loginUiState.emailError
                )
                PasswordTextFieldComponent(
                    value = stringResource(id = R.string.password),
                    painterResource = painterResource(id = R.drawable.lock),
                    onTextChanged = {
                        loginViewModel.onEvent(LoginUIEvent.PasswordChange(it))
                    },
                    errorStatus = loginViewModel.loginUiState.passwordError
                )

                Spacer(modifier = Modifier.height(20.dp))

                ButtonComponents(
                    value = stringResource(id = R.string.login_in),
                    onButtonClicked = {
                        loginViewModel.login {
                            navController.navigate(Screens.HomeScreen.route)
                        }
                    },
                   isEnabled = loginViewModel.allValidationPassed
                )

                TextComponent(
                    tryingToLogin = 2,
                    onTextSelected = {
                        navController.navigate(Screens.RegistrationScreen.route)
                    },
                )
            }
        }
        if (loginViewModel.loginInProgress) {
            CircularProgressIndicator()
        }
    }

}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun LoginScreenPrev() {
    val context = LocalContext.current
    LoginScreen(NavHostController(context))

}