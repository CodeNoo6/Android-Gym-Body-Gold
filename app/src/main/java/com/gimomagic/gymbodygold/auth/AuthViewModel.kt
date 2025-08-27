package com.gimomagic.gymbodygold.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val authService = FirebaseAuthService()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    private val auth = com.google.firebase.auth.FirebaseAuth.getInstance()

    val error: LiveData<String?> = _error

    init {
        checkCurrentUser()
    }

    private fun checkCurrentUser() {
        val currentUser = authService.getCurrentUser()
        _authState.value = if (currentUser != null) {
            AuthState.Authenticated(currentUser)
        } else {
            AuthState.Unauthenticated
        }
    }

    fun signIn(email: String, password: String) {
        _loading.value = true
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                _loading.value = false
                _error.value = null
                result.user?.let { _authState.value = AuthState.Authenticated(it) }
                    ?: run { _authState.value = AuthState.Unauthenticated }
            }
            .addOnFailureListener { e ->
                _loading.value = false
                val msg = when (e) {
                    is com.google.firebase.auth.FirebaseAuthInvalidUserException ->
                        "El correo no está registrado."
                    is com.google.firebase.auth.FirebaseAuthInvalidCredentialsException ->
                        "Correo o contraseña inválidos."
                    is com.google.firebase.FirebaseNetworkException ->
                        "Error de conexión. Verifica tu internet."
                    else -> e.localizedMessage ?: "Error al iniciar sesión."
                }
                android.util.Log.d("AuthViewModel", "signIn error: $msg")
                _error.postValue(msg)
            }
    }

    fun register(email: String, password: String, displayName: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            authService.registerUser(email, password)
                .onSuccess { user ->
                    // Actualizar el nombre del usuario después del registro
                    authService.updateUserProfile(displayName)
                    _authState.value = AuthState.Authenticated(user!!)
                }
                .onFailure { exception ->
                    _error.value = getErrorMessage(exception)
                    _authState.value = AuthState.Unauthenticated
                }

            _loading.value = false
        }
    }

    fun signOut() {
        authService.signOut()
        _authState.value = AuthState.Unauthenticated
    }

    fun resetPassword(email: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            authService.resetPassword(email)
                .onSuccess {
                    _error.value = "Email de recuperación enviado"
                }
                .onFailure { exception ->
                    _error.value = getErrorMessage(exception)
                }

            _loading.value = false
        }
    }

    fun clearError() {
        _error.value = null
    }

    private fun getErrorMessage(exception: Throwable): String {
        return when (exception) {
            is FirebaseAuthWeakPasswordException -> "La contraseña es muy débil"
            is FirebaseAuthInvalidCredentialsException -> "Email o contraseña inválidos"
            is FirebaseAuthUserCollisionException -> "Ya existe una cuenta con este email"
            is FirebaseAuthInvalidUserException -> "Usuario no encontrado"
            is FirebaseNetworkException -> "Error de conexión. Verifica tu internet"
            else -> exception.message ?: "Error desconocido"
        }
    }
}