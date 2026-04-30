package com.turkcell.libraryapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.turkcell.libraryapp.ui.navigation.Screen
import com.turkcell.libraryapp.ui.viewmodel.AuthState
import com.turkcell.libraryapp.ui.viewmodel.AuthViewModel


@Composable
fun RegisterScreen(onNavigateToLogin: () -> Unit, authViewModel: AuthViewModel) {


//    val authViewModel: AuthViewModel = viewModel() // Navigasyon ekranına taşı.
    val authState by authViewModel.authState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var studentno by remember { mutableStateOf("") }



    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
          onNavigateToLogin()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Text("Kütüphane Sistemi", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Kayıt Ol", fontSize = 20.sp)
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            label = { Text("Tam Ad") },
            onValueChange = { value -> name = value },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            label = { Text("E-posta") },
            onValueChange = { value -> email = value },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            label = { Text("Şifre") },
            onValueChange = { value -> password = value },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation()

        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = studentno,
            label = { Text("Student No") },
            onValueChange = { value -> studentno = value },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation()

        )
        Spacer(modifier = Modifier.height(8.dp))


        if (authState is AuthState.Loading) {
            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        } else {
            Button(onClick = {
                authViewModel.register(email, password, studentno, name)

            }, modifier = Modifier.fillMaxWidth()) {
                Text("Kayıt ol")
            }
        }

        if (authState is AuthState.Error)
            Text((authState as AuthState.Error).message)


    }


}


@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {

//    RegisterScreen(rememberNavController())


}