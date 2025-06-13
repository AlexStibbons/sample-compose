package com.chomp.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.chomp.R
import com.chomp.library.data.FakeHost
import com.chomp.library.ui.SCREEN_PADDING
import com.chomp.library.ui.ShowAlertDialog
import com.chomp.library.ui.ShowCustomDialog
import com.chomp.navigation.NavItem
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun LoginScreen(
    navController: NavHostController = FakeHost(LocalContext.current)
) {
    // will koin injecting cause unpredicted side effects?
    // does hilt not have this issue?
    // seeing how it's all on 1 app, the global context will always exist
    // so, it's not an issue here
    // but if creating an SDK, blindly injecting will fail
    injectLoginModule()

    val loginVM: LoginViewModel = koinViewModel()

    val state by loginVM.uiState.collectAsStateWithLifecycle()
    when (val uiState = state) {
        LoginViewModel.LoginState.Error -> ShowAlertDialog("Something went wrong...") {  }
        LoginViewModel.LoginState.Initial -> LoginContent { one, two -> loginVM.processInput(one, two) }
        LoginViewModel.LoginState.Success -> {
            loginVM.resetState()
            navController.navigate(NavItem.HomeList)
            // clear from backstack when navigated away from screen
        }

        LoginViewModel.LoginState.Loading -> {}
    }
}

@Composable
internal fun LoginContent(
    processInput: (String, String) -> Unit
) {
    /**
     * Good practice/pattern is to observe all variables
     * from the view model; remembering locally can be unreliable
     * */
    var showAlert by rememberSaveable { mutableStateOf(false) }
    var showCustom by rememberSaveable { mutableStateOf(false) }

    var input1 by rememberSaveable { mutableStateOf("") }

    var input2 by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(SCREEN_PADDING)) {

        if (showAlert) ShowAlertDialog { showAlert = false }
        if (showCustom) ShowCustomDialog { showCustom = false }

        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = "Image"
            )

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                text = "Some text",
            )

            OutlinedTextField(
                value = input1,
                onValueChange = { input ->
                    input1 = input
                },
                singleLine = true,
                label = { Text("One") },
                placeholder = { Text("Start typing...") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )

            OutlinedTextField(
                value = input2,
                onValueChange = { input ->
                    input2 = input
                },
                singleLine = true,
                label = { Text("Two") },
                placeholder = { Text("Start typing...") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                Button(
                    onClick = { showAlert = true }
                ) { Text("Alert") }

                Button(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    onClick = { showCustom = true }
                ) { Text("Custom") }
            }
        }

        Button(
            onClick = { processInput(input1, input2) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 16.dp, 16.dp, 32.dp),
            enabled = (input1.isNotEmpty() && input2.isNotEmpty())
        ) {
            Text("Open list")
        }

    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoginPreview() {
    LoginContent { one, two -> }
}