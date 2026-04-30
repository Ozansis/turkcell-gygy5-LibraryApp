package com.turkcell.libraryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turkcell.libraryapp.data.model.Profile
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




    private val _profile = MutableStateFlow<Profile?>(null)
    val profile: StateFlow<Profile?> = _profile;
    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            repository
                .signIn(email, password)
                .onSuccess {
                    loadProfileAndSetSuccess() // register tarafıyla burada kod tekrarı olduğu için tek fonksiyon yazıldı
                }
                .onFailure { ex ->
                    _authState.value = AuthState.Error(ex.message ?: "Giriş başarısız")
                }
        }
    }

    fun register(email: String, password: String, studentNo: String?, username: String) {

        if (email.isBlank() || password.isBlank() || username.isBlank()) {

            _authState.value = AuthState.Error("Alanlar Boş Bırakılamaz!")
            return
        }



        viewModelScope.launch {
            _authState.value = AuthState.Loading
            repository.register(email, password, studentNo, username)
                .onSuccess {
                   loadProfileAndSetSuccess()
                }
                .onFailure { ex ->
                    _authState.value = AuthState.Error(ex.message ?: "Kayıt Başarısız")
                }
        }


    }

    fun resetState(){
        _authState.value = AuthState.Idle
    }


    private suspend fun loadProfileAndSetSuccess() {
        val userId = repository.getCurrentUserId()
        if (userId != null) {
            val profile = repository.getProfile(userId)
            _profile.value = profile
            _authState.value = AuthState.Success("student")
        } else {
            _authState.value = AuthState.Error("Profil bulunamadı.")
        }
    }
}