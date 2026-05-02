package com.turkcell.libraryapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.turkcell.libraryapp.ui.viewmodel.AuthState
import com.turkcell.libraryapp.ui.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(onNavigateToLogin: () -> Unit, authViewModel: AuthViewModel) {
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.AutoMirrored.Filled.MenuBook,
                        contentDescription = null,
                        tint = Color(0xFF1A2C42),
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Archivist",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A2C42),
                        fontSize = 24.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Join our digital sanctuary",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(32.dp))

                // Full Name
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("FULL NAME", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A2C42))
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        placeholder = { Text("Ozan Sismanoglu", fontSize = 14.sp) },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(18.dp)) },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color(0xFFF8F9FA),
                            focusedContainerColor = Color(0xFFF8F9FA),
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color(0xFF1A2C42)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Email
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("EMAIL ADDRESS", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A2C42))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        placeholder = { Text("ozn@gmail.com", fontSize = 14.sp) },
                        leadingIcon = { Icon(Icons.Default.Mail, contentDescription = null, modifier = Modifier.size(18.dp)) },
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color(0xFFF8F9FA),
                            focusedContainerColor = Color(0xFFF8F9FA),
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color(0xFF1A2C42)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Password
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("PASSWORD", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A2C42))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        placeholder = { Text("********", fontSize = 14.sp) },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(18.dp)) },
                        shape = RoundedCornerShape(12.dp),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color(0xFFF8F9FA),
                            focusedContainerColor = Color(0xFFF8F9FA),
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color(0xFF1A2C42)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Student No
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("STUDENT NO", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A2C42))
                    OutlinedTextField(
                        value = studentno,
                        onValueChange = { studentno = it },
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        placeholder = { Text("20241001", fontSize = 14.sp) },
                        leadingIcon = { Icon(Icons.Default.School, contentDescription = null, modifier = Modifier.size(18.dp)) },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color(0xFFF8F9FA),
                            focusedContainerColor = Color(0xFFF8F9FA),
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color(0xFF1A2C42)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Sign Up Button
                Button(
                    onClick = { authViewModel.register(email, password, studentno, name) },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A2C42)),
                    enabled = authState !is AuthState.Loading
                ) {
                    if (authState is AuthState.Loading) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Sign Up", fontWeight = FontWeight.Bold, color = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                        }
                    }
                }

                if (authState is AuthState.Error) {
                    Text(
                        text = (authState as AuthState.Error).message,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Login Link
                Row {
                    Text("Already have a library card? ", fontSize = 13.sp, color = Color.Gray)
                    TextButton(
                        onClick = onNavigateToLogin,
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.height(20.dp)
                    ) {
                        Text("Log in", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A2C42))
                    }
                }
            }
        }
    }
}

