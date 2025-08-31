package com.gimomagic.gymbodygold.ui.screens.loginScreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gimomagic.gymbodygold.ui.theme.GymBodyGoldTheme
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import com.gimomagic.gymbodygold.R
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.ui.graphics.Brush

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.LaunchedEffect
import com.gimomagic.gymbodygold.auth.AuthViewModel
import com.gimomagic.gymbodygold.auth.AuthState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext

// Data class for user registration
data class UserRegistrationData(
    val email: String,
    val displayName: String,
    val idTipoDocumento: String,
    val numeroDocumento: String,
    val nombre: String,
    val apellido: String,
    val telefono: String,
    val fechaNacimiento: String,
    val direccion: String,
    val activo: Boolean = true,
    val idGenero: String,
    val peso: String? = null,
    val estatura: String? = null,
    val rol: String = "usuario"
)

fun ShowCenteredToast(context: android.content.Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).apply {
        show()
    }
}

@Composable
fun LoginScreens(
    authViewModel: AuthViewModel = viewModel(),
    onForgotPasswordClick: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    onRegisterSuccess: () -> Unit = {} // Add callback for registration success
) {
    var isLoginScreen by remember { mutableStateOf(true) }

    // Observar estados de Firebase Auth
    val authState by authViewModel.authState.observeAsState()
    val loading by authViewModel.loading.observeAsState(false)
    val error by authViewModel.error.observeAsState()
    val context = LocalContext.current

    // Manejar el éxito del login/registro
    LaunchedEffect(authState) {
        if (authState is AuthState.Authenticated) {
            if (isLoginScreen) {
                onLoginSuccess()
            } else {
                onRegisterSuccess()
            }
        }
    }

    LaunchedEffect(error) {
        error?.let { errorMessage ->
            val displayMessage = if (isLoginScreen) {
                "Las credenciales proporcionadas no son válidas"
            } else {
                errorMessage
            }
            ShowCenteredToast(context, displayMessage)
            authViewModel.clearError()
        }
    }

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF19181C))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Logo section
                Spacer(modifier = Modifier.height(30.dp))
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFFFFBF33).copy(alpha = 0.6f),
                                    Color(0xFFFFBF33).copy(alpha = 0.3f),
                                    Color(0xFFFFBF33).copy(alpha = 0.1f),
                                    Color.Transparent
                                ),
                                radius = 180f
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(140.dp)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        Color(0xFFFFBF33).copy(alpha = 0.8f),
                                        Color(0xFFFFBF33).copy(alpha = 0.4f),
                                        Color.Transparent
                                    ),
                                    radius = 140f
                                ),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = listOf(
                                            Color(0xFFFFD700),
                                            Color(0xFFFFBF33),
                                        ),
                                        radius = 60f
                                    ),
                                    shape = CircleShape
                                )
                                .border(
                                    width = 2.dp,
                                    color = Color(0xFFB8860B),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "Gym Body Gold Logo",
                                modifier = Modifier
                                    .size(115.dp)
                                    .clip(CircleShape)
                            )
                        }
                    }
                }
                Text(
                    text = "Gym Body Gold",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFBF33)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = if (isLoginScreen) "Bienvenido de vuelta" else "Crear Cuenta",
                    fontSize = 16.sp,
                    color = Color(0xFFFFBF33)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Tab buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color(0xFF2C2B30)),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = { isLoginScreen = true },
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isLoginScreen) Color(0xFFFFBF33) else Color.Transparent,
                            contentColor = if (isLoginScreen) Color.Black else Color.White
                        )
                    ) {
                        Text(text = "Iniciar Sesión")
                    }
                    Button(
                        onClick = { isLoginScreen = false },
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!isLoginScreen) Color(0xFFFFBF33) else Color.Transparent,
                            contentColor = if (!isLoginScreen) Color.Black else Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(0.dp)
                    ) {
                        Text(text = "Crear Cuenta")
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                if (isLoginScreen) {
                    LoginContentWithFirebase(
                        authViewModel = authViewModel,
                        loading = loading,
                        onForgotPasswordClick = onForgotPasswordClick
                    )
                } else {
                    CreateAccountContentWithFirebase(
                        authViewModel = authViewModel,
                        loading = loading
                    )
                }
            }
        }
    }
}

@Composable
fun LoginContentWithFirebase(
    authViewModel: AuthViewModel,
    loading: Boolean,
    onForgotPasswordClick: () -> Unit = {}
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val goldColor = Color(0xFFFFBF33)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Email Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            placeholder = { Text("Email", color = Color.Gray) },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email",
                    tint = goldColor,
                    modifier = Modifier.size(20.dp)
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF2C2B30),
                unfocusedContainerColor = Color(0xFF2C2B30),
                focusedBorderColor = goldColor,
                unfocusedBorderColor = goldColor.copy(alpha = 0.5f),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = goldColor,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            placeholder = { Text("Contraseña", color = Color.Gray) },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password",
                    tint = goldColor,
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = { passwordVisible = !passwordVisible }
                ) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                        tint = goldColor.copy(alpha = 0.7f),
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF2C2B30),
                unfocusedContainerColor = Color(0xFF2C2B30),
                focusedBorderColor = goldColor,
                unfocusedBorderColor = goldColor.copy(alpha = 0.5f),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = goldColor,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Login Button
        Button(
            onClick = {
                if (email.isNotBlank() && password.isNotBlank()) {
                    authViewModel.signIn(email, password)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = goldColor,
                contentColor = Color.Black
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 4.dp,
                pressedElevation = 8.dp
            ),
            enabled = !loading && email.isNotBlank() && password.isNotBlank()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Iniciando...",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Iniciar Sesión",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Forgot Password Link
        Text(
            text = "¿Olvidaste tu contraseña?",
            color = goldColor.copy(alpha = 0.8f),
            fontSize = 14.sp,
            modifier = Modifier
                .clickable {
                    onForgotPasswordClick()
                }
                .padding(vertical = 8.dp),
            textDecoration = TextDecoration.Underline
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccountContentWithFirebase(
    authViewModel: AuthViewModel,
    loading: Boolean
) {
    // Form states
    var documentTypeExpanded by remember { mutableStateOf(false) }
    val documentTypes = listOf("CC", "TI", "CE", "Pasaporte")
    var selectedDocumentType by remember { mutableStateOf(documentTypes[0]) }
    var documentNumber by rememberSaveable { mutableStateOf("") }
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var weight by rememberSaveable { mutableStateOf("") }
    var height by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }
    var selectedGender by rememberSaveable { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }

    // Validation states
    var documentError by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf("") }
    var lastNameError by remember { mutableStateOf("") }
    var usernameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf("") }
    var birthDateError by remember { mutableStateOf("") }
    var genderError by remember { mutableStateOf("") }

    // Additional states
    var showDatePicker by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val goldColor = Color(0xFFFFBF33)
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    // Validation functions
    fun validateDocumentNumber(number: String, type: String): String {
        return when (type) {
            "CC", "TI", "CE" -> {
                when {
                    number.isEmpty() -> "Campo obligatorio"
                    !number.all { it.isDigit() } -> "Solo se permiten números"
                    number.length < 7 -> "Mínimo 7 dígitos"
                    else -> ""
                }
            }
            "Pasaporte" -> {
                when {
                    number.isEmpty() -> "Campo obligatorio"
                    number.length < 6 -> "Mínimo 6 caracteres"
                    else -> ""
                }
            }
            else -> ""
        }
    }

    fun validateName(name: String): String {
        return when {
            name.isEmpty() -> "Campo obligatorio"
            !name.all { it.isLetter() || it.isWhitespace() } -> "Solo se permiten letras"
            name.trim().length < 2 -> "Mínimo 2 caracteres"
            else -> ""
        }
    }

    fun validateUsername(username: String): String {
        return when {
            username.isEmpty() -> "Campo obligatorio"
            username.length < 3 -> "Mínimo 3 caracteres"
            !username.all { it.isLetterOrDigit() || it == '_' || it == '.' } -> "Solo letras, números, _ y ."
            else -> ""
        }
    }

    fun validateEmail(email: String): String {
        return when {
            email.isEmpty() -> "Campo obligatorio"
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Email inválido"
            else -> ""
        }
    }

    fun validatePassword(password: String): String {
        return when {
            password.isEmpty() -> "Campo obligatorio"
            password.length < 6 -> "Mínimo 6 caracteres"
            !password.any { it.isUpperCase() } -> "Debe contener una mayúscula"
            !password.any { it.isLowerCase() } -> "Debe contener una minúscula"
            !password.any { it.isDigit() } -> "Debe contener un número"
            else -> ""
        }
    }

    fun validatePasswordConfirmation(password: String, confirmation: String): String {
        return when {
            confirmation.isEmpty() -> "Campo obligatorio"
            password != confirmation -> "Las contraseñas no coinciden"
            else -> ""
        }
    }

    fun validatePhone(phone: String): String {
        val digits = phone.replace(Regex("[^\\d]"), "")
        return when {
            phone.isEmpty() -> "Campo obligatorio"
            digits.length < 7 -> "Mínimo 7 dígitos"
            else -> ""
        }
    }

    fun calculateAge(birthDateStr: String): Int {
        val today = Calendar.getInstance()
        val parts = birthDateStr.split("/")
        if (parts.size != 3) return 0

        val birthDate = Calendar.getInstance()
        birthDate.set(
            parts[2].toInt(), // año
            parts[1].toInt() - 1, // mes (0-11)
            parts[0].toInt() // día
        )

        var age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)
        if (today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
            age--
        }
        return age
    }

    fun validateBirthDate(birthDate: String): String {
        return if (birthDate.isEmpty()) {
            "Campo obligatorio"
        } else {
            try {
                val age = calculateAge(birthDate)
                when {
                    age < 14 -> "Debe ser mayor de 14 años"
                    age > 120 -> "Edad no válida"
                    else -> ""
                }
            } catch (e: Exception) {
                "Fecha inválida"
            }
        }
    }

    fun validateField(field: String, value: String) {
        when (field) {
            "documentNumber" -> documentError = validateDocumentNumber(value, selectedDocumentType)
            "firstName" -> nameError = validateName(value)
            "lastName" -> lastNameError = validateName(value)
            "username" -> usernameError = validateUsername(value)
            "email" -> emailError = validateEmail(value)
            "password" -> {
                passwordError = validatePassword(value)
                if (confirmPassword.isNotEmpty()) {
                    confirmPasswordError = validatePasswordConfirmation(value, confirmPassword)
                }
            }
            "confirmPassword" -> confirmPasswordError = validatePasswordConfirmation(password, value)
            "phone" -> phoneError = validatePhone(value)
            "birthDate" -> birthDateError = validateBirthDate(value)
        }
    }

    fun validateCompleteForm(): Boolean {
        genderError = if (selectedGender.isEmpty()) "Debe seleccionar un género" else ""

        validateField("documentNumber", documentNumber)
        validateField("firstName", firstName)
        validateField("lastName", lastName)
        validateField("username", username)
        validateField("email", email)
        validateField("password", password)
        validateField("confirmPassword", confirmPassword)
        validateField("phone", phone)
        validateField("birthDate", birthDate)

        val hasErrors = listOf(
            documentError, nameError, lastNameError, usernameError,
            emailError, passwordError, confirmPasswordError, phoneError,
            birthDateError, genderError
        ).any { it.isNotEmpty() }

        return !hasErrors
    }

    fun handleFormSubmission() {
        if (validateCompleteForm()) {
            // Create user registration data following the Swift structure
            val userRegistrationData = UserRegistrationData(
                email = email,
                displayName = "$firstName $lastName",
                idTipoDocumento = selectedDocumentType,
                numeroDocumento = documentNumber,
                nombre = firstName,
                apellido = lastName,
                telefono = phone,
                fechaNacimiento = birthDate,
                direccion = address,
                activo = true,
                idGenero = selectedGender,
                peso = if (weight.isNotEmpty()) weight else null,
                estatura = if (height.isNotEmpty()) height else null,
                rol = "usuario"
            )

            // Call Firebase registration with user data
            authViewModel.signUpWithUserData(email, password, userRegistrationData)
        } else {
            // Scroll to top to see errors
            coroutineScope.launch {
                scrollState.animateScrollTo(0)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // INFORMACIÓN DE DOCUMENTO
        Text(text = "Información de Documento", color = goldColor, fontSize = 14.sp, textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))

        // Document type dropdown
        ExposedDropdownMenuBox(
            expanded = documentTypeExpanded,
            onExpandedChange = { documentTypeExpanded = !documentTypeExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedDocumentType,
                onValueChange = {},
                readOnly = true,
                label = { Text("Tipo de Documento", color = Color.Gray) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = documentTypeExpanded) },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Documento", tint = goldColor)
                },
                modifier = Modifier.fillMaxWidth().menuAnchor(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF2C2B30),
                    unfocusedContainerColor = Color(0xFF2C2B30),
                    focusedBorderColor = goldColor,
                    unfocusedBorderColor = goldColor,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )
            ExposedDropdownMenu(
                expanded = documentTypeExpanded,
                onDismissRequest = { documentTypeExpanded = false },
                modifier = Modifier.background(Color(0xFF2C2B30))
            ) {
                documentTypes.forEach { type ->
                    DropdownMenuItem(
                        text = { Text(text = type, color = Color.White) },
                        onClick = {
                            selectedDocumentType = type
                            documentTypeExpanded = false
                            validateField("documentNumber", documentNumber)
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Document number with validation
        ValidatedTextField(
            value = documentNumber,
            onValueChange = { newValue ->
                val filteredValue = when (selectedDocumentType) {
                    "CC", "TI", "CE" -> newValue.filter { it.isDigit() }
                    "Pasaporte" -> newValue.filter { it.isLetterOrDigit() }
                    else -> newValue
                }
                documentNumber = filteredValue
                validateField("documentNumber", filteredValue)
            },
            label = "Número de Documento",
            leadingIcon = Icons.Default.Create,
            errorMessage = documentError,
            keyboardType = if (selectedDocumentType in listOf("CC", "TI", "CE")) KeyboardType.Number else KeyboardType.Text,
            goldColor = goldColor
        )

        Spacer(modifier = Modifier.height(32.dp))

        // INFORMACIÓN PERSONAL
        Text(text = "Información Personal", color = goldColor, fontSize = 14.sp, textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))

        // Name fields
        ValidatedTextField(
            value = firstName,
            onValueChange = { newValue ->
                val filteredValue = newValue.filter { it.isLetter() || it.isWhitespace() }
                firstName = filteredValue
                validateField("firstName", filteredValue)
            },
            label = "Nombre",
            leadingIcon = Icons.Default.Person,
            errorMessage = nameError,
            goldColor = goldColor
        )
        Spacer(modifier = Modifier.height(16.dp))

        ValidatedTextField(
            value = lastName,
            onValueChange = { newValue ->
                val filteredValue = newValue.filter { it.isLetter() || it.isWhitespace() }
                lastName = filteredValue
                validateField("lastName", filteredValue)
            },
            label = "Apellido",
            leadingIcon = Icons.Default.Person,
            errorMessage = lastNameError,
            goldColor = goldColor
        )
        Spacer(modifier = Modifier.height(16.dp))

        ValidatedTextField(
            value = username,
            onValueChange = { newValue ->
                val filteredValue = newValue.filter { it.isLetterOrDigit() || it == '_' || it == '.' }
                username = filteredValue
                validateField("username", filteredValue)
            },
            label = "Nombre de Usuario",
            leadingIcon = Icons.Default.AccountCircle,
            errorMessage = usernameError,
            goldColor = goldColor
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Birth date picker
        OutlinedTextField(
            value = birthDate,
            onValueChange = { },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true },
            label = { Text("Fecha de Nacimiento", color = Color.Gray) },
            placeholder = { Text("Selecciona tu fecha de nacimiento", color = Color.Gray) },
            shape = RoundedCornerShape(12.dp),
            isError = birthDateError.isNotEmpty(),
            leadingIcon = {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Fecha", tint = goldColor)
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Seleccionar fecha",
                    tint = goldColor.copy(alpha = 0.7f),
                    modifier = Modifier.clickable { showDatePicker = true }
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF2C2B30),
                unfocusedContainerColor = Color(0xFF2C2B30),
                disabledContainerColor = Color(0xFF2C2B30),
                focusedBorderColor = if (birthDateError.isNotEmpty()) Color.Red else goldColor,
                unfocusedBorderColor = if (birthDateError.isNotEmpty()) Color.Red else goldColor,
                disabledBorderColor = if (birthDateError.isNotEmpty()) Color.Red else goldColor,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                disabledTextColor = Color.White,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
                disabledLabelColor = Color.Gray
            )
        )
        if (birthDateError.isNotEmpty()) {
            Text(
                text = birthDateError,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }

        // Show calculated age if birth date is provided
        if (birthDate.isNotEmpty()) {
            val calculatedAge = calculateAge(birthDate)
            if (calculatedAge > 0) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Card(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = goldColor.copy(alpha = 0.1f)
                        )
                    ) {
                        Text(
                            text = "Edad: $calculatedAge años",
                            color = goldColor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Gender selection
        Text(text = "Género", color = goldColor, fontSize = 14.sp, textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth())
        if (genderError.isNotEmpty()) {
            Text(
                text = genderError,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 0.dp, top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GenderButton(
                text = "Masculino",
                isSelected = selectedGender == "Masculino",
                onClick = {
                    selectedGender = "Masculino"
                    genderError = ""
                },
                modifier = Modifier.weight(1f)
            )
            GenderButton(
                text = "Femenino",
                isSelected = selectedGender == "Femenino",
                onClick = {
                    selectedGender = "Femenino"
                    genderError = ""
                },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // INFORMACIÓN DE CONTACTO
        Text(text = "Información de Contacto", color = goldColor, fontSize = 14.sp, textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))

        // Phone field
        ValidatedTextField(
            value = phone,
            onValueChange = { newValue ->
                phone = newValue
                validateField("phone", newValue)
            },
            label = "Teléfono",
            leadingIcon = Icons.Default.Phone,
            errorMessage = phoneError,
            keyboardType = KeyboardType.Phone,
            goldColor = goldColor
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Address field (optional)
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Dirección", color = Color.Gray) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Home, contentDescription = "Dirección", tint = goldColor)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF2C2B30),
                unfocusedContainerColor = Color(0xFF2C2B30),
                focusedBorderColor = goldColor,
                unfocusedBorderColor = goldColor,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // INFORMACIÓN FÍSICA (OPCIONAL)
        Text(text = "Información Física (Opcional)", color = goldColor, fontSize = 14.sp, textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))

        // Weight and height in a row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = weight,
                    onValueChange = { newValue ->
                        val filteredValue = newValue.filter { it.isDigit() }
                        weight = filteredValue
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Peso (kg)", color = Color.Gray) },
                    placeholder = { Text("Ej: 70", color = Color.Gray) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Create, contentDescription = "Peso", tint = goldColor)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF2C2B30),
                        unfocusedContainerColor = Color(0xFF2C2B30),
                        focusedBorderColor = goldColor,
                        unfocusedBorderColor = goldColor,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = height,
                    onValueChange = { newValue ->
                        val filteredValue = newValue.filter { it.isDigit() }
                        height = filteredValue
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Estatura (cm)", color = Color.Gray) },
                    placeholder = { Text("Ej: 175", color = Color.Gray) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Create, contentDescription = "Estatura", tint = goldColor)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF2C2B30),
                        unfocusedContainerColor = Color(0xFF2C2B30),
                        focusedBorderColor = goldColor,
                        unfocusedBorderColor = goldColor,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // CREDENCIALES DE ACCESO
        Text(text = "Credenciales de Acceso", color = goldColor, fontSize = 14.sp, textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))

        // Email field
        ValidatedTextField(
            value = email,
            onValueChange = { newValue ->
                email = newValue
                validateField("email", newValue)
            },
            label = "Email",
            leadingIcon = Icons.Default.Email,
            errorMessage = emailError,
            keyboardType = KeyboardType.Email,
            goldColor = goldColor
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Password fields
        ValidatedPasswordField(
            value = password,
            onValueChange = { newValue ->
                password = newValue
                validateField("password", newValue)
            },
            label = "Contraseña",
            errorMessage = passwordError,
            isPasswordVisible = passwordVisible,
            onTogglePasswordVisibility = { passwordVisible = !passwordVisible },
            goldColor = goldColor
        )
        Spacer(modifier = Modifier.height(16.dp))

        ValidatedPasswordField(
            value = confirmPassword,
            onValueChange = { newValue ->
                confirmPassword = newValue
                validateField("confirmPassword", newValue)
            },
            label = "Confirmar Contraseña",
            errorMessage = confirmPasswordError,
            isPasswordVisible = confirmPasswordVisible,
            onTogglePasswordVisibility = { confirmPasswordVisible = !confirmPasswordVisible },
            goldColor = goldColor
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Registration button
        Button(
            onClick = { handleFormSubmission() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = !loading,
            colors = ButtonDefaults.buttonColors(
                containerColor = goldColor,
                contentColor = Color.Black,
                disabledContainerColor = goldColor.copy(alpha = 0.6f)
            )
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Creando cuenta...", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            } else {
                Text(text = "Crear Cuenta", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }

    // Date picker dialog
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val calendar = Calendar.getInstance()
                            calendar.timeInMillis = millis
                            birthDate = String.format(
                                "%02d/%02d/%04d",
                                calendar.get(Calendar.DAY_OF_MONTH),
                                calendar.get(Calendar.MONTH) + 1,
                                calendar.get(Calendar.YEAR)
                            )
                            validateField("birthDate", birthDate)
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("Confirmar", color = goldColor)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePicker = false }
                ) {
                    Text("Cancelar", color = Color.Gray)
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = Color(0xFF2C2B30),
            )
        ) {
            DatePicker(
                state = datePickerState,
                title = {
                    Text(
                        text = "Seleccionar fecha de nacimiento",
                        color = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )
                },
                headline = {
                    Text(
                        text = datePickerState.selectedDateMillis?.let {
                            val calendar = Calendar.getInstance()
                            calendar.timeInMillis = it
                            String.format(
                                "%02d/%02d/%04d",
                                calendar.get(Calendar.DAY_OF_MONTH),
                                calendar.get(Calendar.MONTH) + 1,
                                calendar.get(Calendar.YEAR)
                            )
                        } ?: "Fecha no seleccionada",
                        color = goldColor,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                },
                showModeToggle = false,
                colors = DatePickerDefaults.colors(
                    containerColor = Color(0xFF2C2B30),
                    titleContentColor = Color.White,
                    headlineContentColor = goldColor,
                    weekdayContentColor = Color.Gray,
                    subheadContentColor = Color.Gray,
                    navigationContentColor = goldColor,
                    yearContentColor = Color.White,
                    disabledYearContentColor = Color.Gray,
                    currentYearContentColor = goldColor,
                    selectedYearContentColor = Color.Black,
                    selectedYearContainerColor = goldColor,
                    dayContentColor = Color.White,
                    disabledDayContentColor = Color.Gray,
                    selectedDayContentColor = Color.Black,
                    disabledSelectedDayContentColor = Color.Gray,
                    selectedDayContainerColor = goldColor,
                    disabledSelectedDayContainerColor = goldColor.copy(alpha = 0.3f),
                    todayContentColor = goldColor,
                    todayDateBorderColor = goldColor,
                    dayInSelectionRangeContentColor = Color.White,
                    dayInSelectionRangeContainerColor = goldColor.copy(alpha = 0.3f)
                )
            )
        }
    }
}

// Helper components for validation
@Composable
fun ValidatedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: androidx.compose.ui.graphics.vector.ImageVector,
    errorMessage: String,
    goldColor: Color,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier.fillMaxWidth(),
            label = { Text(label, color = Color.Gray) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            isError = errorMessage.isNotEmpty(),
            leadingIcon = {
                Icon(imageVector = leadingIcon, contentDescription = label, tint = goldColor)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF2C2B30),
                unfocusedContainerColor = Color(0xFF2C2B30),
                focusedBorderColor = if (errorMessage.isNotEmpty()) Color.Red else goldColor,
                unfocusedBorderColor = if (errorMessage.isNotEmpty()) Color.Red else goldColor,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
                errorBorderColor = Color.Red,
                errorLabelColor = Color.Red
            )
        )
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun ValidatedPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    errorMessage: String,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    goldColor: Color,
    modifier: Modifier = Modifier
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier.fillMaxWidth(),
            label = { Text(label, color = Color.Gray) },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            isError = errorMessage.isNotEmpty(),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "Contraseña", tint = goldColor)
            },
            trailingIcon = {
                IconButton(onClick = onTogglePasswordVisibility) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (isPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                        tint = goldColor.copy(alpha = 0.7f)
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF2C2B30),
                unfocusedContainerColor = Color(0xFF2C2B30),
                focusedBorderColor = if (errorMessage.isNotEmpty()) Color.Red else goldColor,
                unfocusedBorderColor = if (errorMessage.isNotEmpty()) Color.Red else goldColor,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
                errorBorderColor = Color.Red,
                errorLabelColor = Color.Red
            )
        )
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

// Gender button component
@Composable
fun GenderButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val goldColor = Color(0xFFFFBF33)

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) goldColor else Color(0xFF2C2B30),
            contentColor = if (isSelected) Color.Black else Color.White
        ),
        border = BorderStroke(2.dp, goldColor),
        elevation = if (isSelected) ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        ) else ButtonDefaults.buttonElevation(0.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreensPreview() {
    GymBodyGoldTheme {
        LoginScreens()
    }
}