package com.example.firebaseapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firebaseapp.data.auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class AuthState {
    data object Idle : AuthState()
    data object Loading : AuthState()
    data class Success(val message: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

sealed class NavigationState {
    data object Unauthenticated : NavigationState()
    data object Authenticated : NavigationState()
}

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _navigationState = MutableStateFlow<NavigationState>(NavigationState.Unauthenticated)
    val navigationState: StateFlow<NavigationState> = _navigationState.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private var authStateListenerAdded = false

    init {
        startListeningAuthState()
    }

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun login() {
        if (_email.value.isBlank() || _password.value.isBlank()) {
            _authState.value = AuthState.Error("Email y contrasena son requeridos")
            return
        }
        _authState.value = AuthState.Loading
        repository.signInWithEmailAndPassword(
            _email.value, _password.value,
            onSuccess = {
                _authState.value = AuthState.Success("Sesion Iniciada")
            },
            onFailure = {
                _authState.value = AuthState.Error(it.message ?: "Error al iniciar sesion")
            }
        )
    }

    fun register() {
        if (_email.value.isBlank() || _password.value.isBlank()) {
            _authState.value = AuthState.Error("Email y contrasena son requeridos")
            return
        }
        _authState.value = AuthState.Loading
        repository.createUserWithEmailAndPassword(
            _email.value, _password.value,
            onSuccess = {
                _authState.value = AuthState.Success("Usuario registrado")
            },
            onFailure = {
                _authState.value = AuthState.Error(it.message ?: "Error al registrar")
            }
        )
    }

    fun logout() {
        repository.signOut()
        _authState.value = AuthState.Idle
        _email.value = ""
        _password.value = ""
    }

    fun clearState() {
        _authState.value = AuthState.Idle
    }

    private fun startListeningAuthState() {
        repository.addAuthStateListener { user ->
            _navigationState.value = if (user != null) {
                NavigationState.Authenticated
            } else {
                NavigationState.Unauthenticated
            }
        }
        authStateListenerAdded = true
        val currentUser = repository.getCurrentUser()
        _navigationState.value = if (currentUser != null) {
            NavigationState.Authenticated
        } else {
            NavigationState.Unauthenticated
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (authStateListenerAdded) {
            repository.removeAuthStateListener()
        }
    }

    class Factory(private val repository: AuthRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AuthViewModel(repository) as T
        }
    }
}