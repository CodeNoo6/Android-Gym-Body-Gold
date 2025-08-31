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
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import com.gimomagic.gymbodygold.ui.screens.loginScreens.UserRegistrationData

class AuthViewModel : ViewModel() {
    private val authService = FirebaseAuthService()
    private val auth = com.google.firebase.auth.FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
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

    fun signUpWithUserData(email: String, password: String, userData: UserRegistrationData) {
        _loading.value = true
        _error.value = null

        // First create the Firebase Auth user
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val user = result.user
                if (user != null) {
                    // Calculate age from birth date
                    val calculatedAge = calculateAge(userData.fechaNacimiento)

                    // Prepare user data for Firestore following Swift structure
                    val firestoreUserData = hashMapOf(
                        "uid" to user.uid,
                        "email" to userData.email,
                        "displayName" to userData.displayName,
                        "idTipoDocumento" to userData.idTipoDocumento,
                        "numeroDocumento" to userData.numeroDocumento,
                        "nombre" to userData.nombre,
                        "apellido" to userData.apellido,
                        "telefono" to userData.telefono,
                        "fechaNacimiento" to userData.fechaNacimiento,
                        "direccion" to userData.direccion,
                        "activo" to userData.activo,
                        "idGenero" to userData.idGenero,
                        "edad" to calculatedAge, // Calculated automatically
                        "peso" to userData.peso, // Can be null
                        "estatura" to userData.estatura, // Can be null
                        "fechaCreacion" to Date(),
                        "rol" to userData.rol
                    )

                    // Save user data to Firestore
                    firestore.collection("usuarios")
                        .document(user.uid)
                        .set(firestoreUserData)
                        .addOnSuccessListener {
                            _loading.value = false
                            _error.value = null
                            _authState.value = AuthState.Authenticated(user)
                            android.util.Log.d("AuthViewModel", "User registered successfully in Firestore")
                        }
                        .addOnFailureListener { e ->
                            _loading.value = false
                            _error.value = "Error al guardar datos del usuario: ${e.localizedMessage}"
                            android.util.Log.e("AuthViewModel", "Error saving user data", e)

                            // If Firestore save fails, we might want to delete the auth user
                            // to maintain consistency
                            user.delete()
                        }
                } else {
                    _loading.value = false
                    _error.value = "Error al crear usuario"
                    _authState.value = AuthState.Unauthenticated
                }
            }
            .addOnFailureListener { e ->
                _loading.value = false
                val msg = when (e) {
                    is FirebaseAuthWeakPasswordException -> "La contraseña es muy débil. Debe tener al menos 6 caracteres."
                    is FirebaseAuthInvalidCredentialsException -> "Email inválido"
                    is FirebaseAuthUserCollisionException -> "Ya existe una cuenta con este email"
                    is FirebaseNetworkException -> "Error de conexión. Verifica tu internet"
                    else -> e.localizedMessage ?: "Error al registrar usuario"
                }
                android.util.Log.e("AuthViewModel", "Registration error: $msg", e)
                _error.value = msg
                _authState.value = AuthState.Unauthenticated
            }
    }

    private fun calculateAge(birthDateStr: String): Int {
        return try {
            val parts = birthDateStr.split("/")
            if (parts.size != 3) return 0

            val today = Calendar.getInstance()
            val birthDate = Calendar.getInstance()
            birthDate.set(
                parts[2].toInt(), // year
                parts[1].toInt() - 1, // month (0-11)
                parts[0].toInt() // day
            )

            var age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)
            if (today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
                age--
            }
            age
        } catch (e: Exception) {
            android.util.Log.e("AuthViewModel", "Error calculating age", e)
            0
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