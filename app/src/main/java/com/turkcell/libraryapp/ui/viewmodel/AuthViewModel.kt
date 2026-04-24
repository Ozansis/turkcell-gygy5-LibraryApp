package com.turkcell.libraryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turkcell.libraryapp.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.StringFormat


sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val role: String) : AuthState()
    data class Error(val message: String) : AuthState()
}


class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState;

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            repository
                .signIn(email, password)
                .onSuccess { result -> _authState.value = AuthState.Success("student") }
                .onFailure { ex ->
                    _authState.value = AuthState.Error(ex.message ?: "Giriş başarısız")
                }
        }
    }

    fun register(email: String, password: String, repeatPassword: String, username: String) {

        if (email.isBlank() || password.isBlank() || username.isBlank()) {

            _authState.value = AuthState.Error("Alanlar Boş Bırakılamaz!")
            return
        }

        if (password != repeatPassword) {
            _authState.value = AuthState.Error("Şifre eşleşmiyor")
            return
        }

        viewModelScope.launch {
            _authState.value = AuthState.Loading

            repository.register(email, password, repeatPassword, username)
                .onSuccess { _authState.value = AuthState.Success("student") }
                .onFailure { ex->_authState.value = AuthState.Error(ex.message ?: "Kayıt Başarısız") }


        }


    }
}