package com.gimomagic.gymbodygold.ui.screens.loginScreens

import androidx.compose.foundation.BorderStroke
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

@Composable
fun LoginScreens() {
    var isLoginScreen by remember { mutableStateOf(true) }

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
            // Títulos y pestañas
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = if (isLoginScreen) "Gym Body Gold" else "Crear Cuenta",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = if (isLoginScreen) "Bienvenido de vuelta" else "",
                fontSize = 16.sp,
                color = Color.LightGray
            )
            Spacer(modifier = Modifier.height(16.dp))

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
                        containerColor = if (isLoginScreen) Color(0xFFBEA314) else Color.Transparent,
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
                        containerColor = if (!isLoginScreen) Color(0xFFBEA314) else Color.Transparent,
                        contentColor = if (!isLoginScreen) Color.Black else Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(0.dp)
                ) {
                    Text(text = "Crear Cuenta")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (isLoginScreen) {
                LoginContent()
            } else {
                CreateAccountContent()
            }
        }
    }
}

@Composable
fun LoginContent() {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val goldColor = Color(0xFFBEA314)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Email Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Email", color = Color(0xFFBEA314)) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, "Icono de contraseña",tint = Color(0xFFBEA314))
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF2C2B30),
                unfocusedContainerColor = Color(0xFF2C2B30),
                disabledContainerColor = Color(0xFF2C2B30),
                focusedBorderColor = goldColor,
                unfocusedBorderColor = goldColor,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Contraseña", color = Color(0xFFBEA314)) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "Icono de candado",tint = Color(0xFFBEA314))
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF2C2B30),
                unfocusedContainerColor = Color(0xFF2C2B30),
                disabledContainerColor = Color(0xFF2C2B30),
                focusedBorderColor = goldColor,
                unfocusedBorderColor = goldColor,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Login Button
        Button(
            onClick = { /* Handle login click */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFBEA314),
                contentColor = Color.Black
            ),
        ) {
            Text(text = "Iniciar Sesión", fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Forgot Password Link
        Text(
            text = "¿Olvidaste tu contraseña?",
            color = Color.LightGray,
            fontSize = 14.sp,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable { /* Handle forgot password click */ }
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccountContent() {
    var documentTypeExpanded by remember { mutableStateOf(false) }
    val documentTypes = listOf("CC", "TI", "CE", "Pasaporte")
    var selectedDocumentType by remember { mutableStateOf(documentTypes[0]) }

    var documentNumber by rememberSaveable { mutableStateOf("") }
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var age by rememberSaveable { mutableStateOf("") }
    var weight by rememberSaveable { mutableStateOf("") }
    var height by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }

    var selectedGender by rememberSaveable { mutableStateOf("") }
    val goldColor = Color(0xFFBEA314)
    val calendar = remember { Calendar.getInstance() }

    // Estados para errores de validación
    var documentError by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf("") }
    var lastNameError by remember { mutableStateOf("") }
    var usernameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf("") }
    var ageError by remember { mutableStateOf("") }
    var weightError by remember { mutableStateOf("") }
    var heightError by remember { mutableStateOf("") }

    // Estado para controlar el scroll automático
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    var birthDate by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }

    var showApplicationDatePicker by remember { mutableStateOf(false) }
    val applicationDatePickerState = rememberDatePickerState()
    var applicationDate by remember { mutableStateOf("") }
    // Funciones de validación
    fun validateDocumentNumber(number: String, type: String): String {
        return when (type) {
            "CC", "TI", "CE" -> {
                if (number.isEmpty()) "Campo obligatorio"
                else if (!number.all { it.isDigit() }) "Solo se permiten números"
                else if (number.length < 7) "Mínimo 7 dígitos"
                else ""
            }
            "Pasaporte" -> {
                if (number.isEmpty()) "Campo obligatorio"
                else if (number.length < 6) "Mínimo 6 caracteres"
                else ""
            }
            else -> ""
        }
    }

    fun formatDate(millis: Long?): String {
        return if (millis != null) {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = millis
            String.format(
                "%02d/%02d/%04d",
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR)
            )
        } else {
            ""
        }
    }
    fun validateName(name: String): String {
        return if (name.isEmpty()) "Campo obligatorio"
        else if (!name.all { it.isLetter() || it.isWhitespace() }) "Solo se permiten letras"
        else if (name.trim().length < 2) "Mínimo 2 caracteres"
        else ""
    }

    fun validateUsername(username: String): String {
        return if (username.isEmpty()) "Campo obligatorio"
        else if (username.length < 3) "Mínimo 3 caracteres"
        else if (!username.all { it.isLetterOrDigit() || it == '_' || it == '.' }) "Solo letras, números, _ y ."
        else ""
    }

    fun validateEmail(email: String): String {
        return if (email.isEmpty()) "Campo obligatorio"
        else if (!email.contains("@")) "Debe contener @"
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Email inválido"
        else ""
    }

    fun validatePassword(password: String): String {
        return if (password.isEmpty()) "Campo obligatorio"
        else if (password.length < 6) "Mínimo 6 caracteres"
        else ""
    }

    fun validatePhone(phone: String): String {
        return if (phone.isEmpty()) "Campo obligatorio"
        else if (!phone.all { it.isDigit() || it == '+' || it == '-' || it == '(' || it == ')' || it.isWhitespace() }) "Formato de teléfono inválido"
        else if (phone.replace(Regex("[^\\d]"), "").length < 7) "Mínimo 7 dígitos"
        else ""
    }

    fun validateNumber(value: String, fieldName: String, min: Int? = null, max: Int? = null): String {
        return if (value.isNotEmpty()) {
            if (!value.all { it.isDigit() }) "Solo números"
            else {
                val number = value.toIntOrNull()
                when {
                    number == null -> "Número inválido"
                    min != null && number < min -> "Mínimo $min"
                    max != null && number > max -> "Máximo $max"
                    else -> ""
                }
            }
        } else ""
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Sección de Información de Documento
        Text(text = "Información de Documento", color = Color(0xFFBEA314), fontSize = 14.sp,textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth() )
        Spacer(modifier = Modifier.height(16.dp))

        // Tipo de Documento
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
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Icono de documento",tint = Color(0xFFBEA314))
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF2C2B30),
                    unfocusedContainerColor = Color(0xFF2C2B30),
                    disabledContainerColor = Color(0xFF2C2B30),
                    focusedBorderColor = goldColor,
                    unfocusedBorderColor = goldColor,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White,
                    focusedPlaceholderColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Gray,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray
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
                            // Revalidar número de documento cuando cambie el tipo
                            documentError = validateDocumentNumber(documentNumber, type)
                        },
                        modifier = Modifier.background(Color(0xFF2C2B30))
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Número de Documento
        Column {
            OutlinedTextField(
                value = documentNumber,
                onValueChange = { newValue ->
                    // Filtrar entrada según tipo de documento
                    val filteredValue = when (selectedDocumentType) {
                        "CC", "TI", "CE" -> newValue.filter { it.isDigit() }
                        "Pasaporte" -> newValue.filter { it.isLetterOrDigit() }
                        else -> newValue
                    }
                    documentNumber = filteredValue
                    documentError = validateDocumentNumber(filteredValue, selectedDocumentType)
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Create, contentDescription = "Icono de documento",tint = Color(0xFFBEA314))
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Número de Documento", color = Color.Gray) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (selectedDocumentType in listOf("CC", "TI", "CE")) KeyboardType.Number else KeyboardType.Text
                ),
                isError = documentError.isNotEmpty(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF2C2B30),
                    unfocusedContainerColor = Color(0xFF2C2B30),
                    disabledContainerColor = Color(0xFF2C2B30),
                    focusedBorderColor = if (documentError.isNotEmpty()) Color.Red else goldColor,
                    unfocusedBorderColor = if (documentError.isNotEmpty()) Color.Red else goldColor,
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
            if (documentError.isNotEmpty()) {
                Text(
                    text = documentError,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Sección de Información Personal
        Text(text = "Información Personal", color = Color(0xFFBEA314), fontSize = 14.sp,textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))

        // Nombre
        Column {
            OutlinedTextField(
                value = firstName,
                onValueChange = { newValue ->
                    val filteredValue = newValue.filter { it.isLetter() || it.isWhitespace() }
                    firstName = filteredValue
                    nameError = validateName(filteredValue)
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Nombre", color = Color.Gray) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                isError = nameError.isNotEmpty(),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Icono de Persona",tint = Color(0xFFBEA314))
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF2C2B30),
                    unfocusedContainerColor = Color(0xFF2C2B30),
                    disabledContainerColor = Color(0xFF2C2B30),
                    focusedBorderColor = if (nameError.isNotEmpty()) Color.Red else goldColor,
                    unfocusedBorderColor = if (nameError.isNotEmpty()) Color.Red else goldColor,
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
            if (nameError.isNotEmpty()) {
                Text(
                    text = nameError,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Apellido
        Column {
            OutlinedTextField(
                value = lastName,
                onValueChange = { newValue ->
                    val filteredValue = newValue.filter { it.isLetter() || it.isWhitespace() }
                    lastName = filteredValue
                    lastNameError = validateName(filteredValue)
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Apellido", color = Color.Gray) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                isError = lastNameError.isNotEmpty(),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Icono de Persona",tint = Color(0xFFBEA314))
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF2C2B30),
                    unfocusedContainerColor = Color(0xFF2C2B30),
                    disabledContainerColor = Color(0xFF2C2B30),
                    focusedBorderColor = if (lastNameError.isNotEmpty()) Color.Red else goldColor,
                    unfocusedBorderColor = if (lastNameError.isNotEmpty()) Color.Red else goldColor,
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
            if (lastNameError.isNotEmpty()) {
                Text(
                    text = lastNameError,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Nombre de Usuario
        Column {
            OutlinedTextField(
                value = username,
                onValueChange = { newValue ->
                    val filteredValue = newValue.filter { it.isLetterOrDigit() || it == '_' || it == '.' }
                    username = filteredValue
                    usernameError = validateUsername(filteredValue)
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Nombre de Usuario", color = Color.Gray) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                isError = usernameError.isNotEmpty(),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Icono de Usuario",tint = Color(0xFFBEA314))
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF2C2B30),
                    unfocusedContainerColor = Color(0xFF2C2B30),
                    disabledContainerColor = Color(0xFF2C2B30),
                    focusedBorderColor = if (usernameError.isNotEmpty()) Color.Red else goldColor,
                    unfocusedBorderColor = if (usernameError.isNotEmpty()) Color.Red else goldColor,
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
            if (usernameError.isNotEmpty()) {
                Text(
                    text = usernameError,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Fecha de Nacimiento
        OutlinedTextField(
            value = birthDate,
            onValueChange = { },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true },
            label = { Text("Fecha de Nacimiento", color = Color.Gray) },
            shape = RoundedCornerShape(12.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Icono de Fecha",
                    tint = Color(0xFFBEA314)
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF2C2B30),
                unfocusedContainerColor = Color(0xFF2C2B30),
                disabledContainerColor = Color(0xFF2C2B30),
                focusedBorderColor = Color(0xFFBEA314),
                unfocusedBorderColor = Color(0xFFBEA314),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray
            )
        )

        // Selector de fecha
        if (showDatePicker) {
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = { showApplicationDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            applicationDate = formatDate(applicationDatePickerState.selectedDateMillis)
                        }
                    ) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showApplicationDatePicker = false }
                    ) {
                        Text("Cancelar")
                    }
                }
            ) {
                DatePicker(
                    state = applicationDatePickerState,
                    colors = DatePickerDefaults.colors(
                        selectedDayContainerColor = Color(0xFF08CAF7),
                        todayContentColor = Color(0xFF08CAF7),
                        todayDateBorderColor = Color(0xFF08CAF7)
                    )
                )
            }
        }

        // Selector de hora
        if (showTimePicker) {
            val timeState = rememberTimePickerState()
            AlertDialog(
                onDismissRequest = { showTimePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            selectedDateMillis?.let { dateMillis ->
                                val cal = Calendar.getInstance()
                                cal.timeInMillis = dateMillis
                                cal.set(Calendar.HOUR_OF_DAY, timeState.hour)
                                cal.set(Calendar.MINUTE, timeState.minute)
                                birthDate = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
                                    .format(cal.time)
                            }
                            showTimePicker = false
                        }
                    ) { Text("OK") }
                },
                dismissButton = {
                    TextButton(onClick = { showTimePicker = false }) { Text("Cancel") }
                },
                text = {
                    TimePicker(state = timeState)
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sección de Género
        Text(
            text = "Género",
            color = Color(0xFFBEA314),
            fontSize = 14.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(50.dp)
        ) {
            GenderButton(
                text = "Masculino",
                isSelected = selectedGender == "Masculino",
                onClick = {
                    selectedGender = "Masculino"
                    coroutineScope.launch {
                        delay(50)
                        scrollState.animateScrollTo(scrollState.value + 400)
                    }
                }
            )
            GenderButton(
                text = "Femenino",
                isSelected = selectedGender == "Femenino",
                onClick = {
                    selectedGender = "Femenino"
                    coroutineScope.launch {
                        delay(50)
                        scrollState.animateScrollTo(scrollState.value + 400)
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        // Sección de Información de Contacto
        Text(text = "Información de Contacto", color = Color(0xFFBEA314), fontSize = 14.sp,textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))

        // Teléfono
        Column {
            OutlinedTextField(
                value = phone,
                onValueChange = { newValue ->
                    phone = newValue
                    phoneError = validatePhone(newValue)
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Teléfono", color = Color.Gray) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                isError = phoneError.isNotEmpty(),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Phone, contentDescription = "Icono de telefono",tint = Color(0xFFBEA314))
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF2C2B30),
                    unfocusedContainerColor = Color(0xFF2C2B30),
                    disabledContainerColor = Color(0xFF2C2B30),
                    focusedBorderColor = if (phoneError.isNotEmpty()) Color.Red else goldColor,
                    unfocusedBorderColor = if (phoneError.isNotEmpty()) Color.Red else goldColor,
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
            if (phoneError.isNotEmpty()) {
                Text(
                    text = phoneError,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Dirección
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Dirección", color = Color.Gray) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Home, contentDescription = "Icono de dirección",tint = Color(0xFFBEA314))
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF2C2B30),
                unfocusedContainerColor = Color(0xFF2C2B30),
                disabledContainerColor = Color(0xFF2C2B30),
                focusedBorderColor = goldColor,
                unfocusedBorderColor = goldColor,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Sección de Información Física
        Text(text = "Información Física (Opcional)", color = Color(0xFFBEA314), fontSize = 14.sp, textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))

        // Edad, Peso
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = age,
                    onValueChange = { newValue ->
                        val filteredValue = newValue.filter { it.isDigit() }
                        age = filteredValue
                        ageError = validateNumber(filteredValue, "Edad", 1, 120)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Edad", color = Color.Gray) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = ageError.isNotEmpty(),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Face, contentDescription = "Icono de Edad",tint = Color(0xFFBEA314))
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF2C2B30),
                        unfocusedContainerColor = Color(0xFF2C2B30),
                        disabledContainerColor = Color(0xFF2C2B30),
                        focusedBorderColor = if (ageError.isNotEmpty()) Color.Red else goldColor,
                        unfocusedBorderColor = if (ageError.isNotEmpty()) Color.Red else goldColor,
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
                if (ageError.isNotEmpty()) {
                    Text(
                        text = ageError,
                        color = Color.Red,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = weight,
                    onValueChange = { newValue ->
                        val filteredValue = newValue.filter { it.isDigit() }
                        weight = filteredValue
                        weightError = validateNumber(filteredValue, "Peso", 20, 300)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Peso (kg)", color = Color.Gray) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = weightError.isNotEmpty(),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Create, contentDescription = "Icono de Peso",tint = Color(0xFFBEA314))
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF2C2B30),
                        unfocusedContainerColor = Color(0xFF2C2B30),
                        disabledContainerColor = Color(0xFF2C2B30),
                        focusedBorderColor = if (weightError.isNotEmpty()) Color.Red else goldColor,
                        unfocusedBorderColor = if (weightError.isNotEmpty()) Color.Red else goldColor,
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
                if (weightError.isNotEmpty()) {
                    Text(
                        text = weightError,
                        color = Color.Red,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Estatura
        Column {
            OutlinedTextField(
                value = height,
                onValueChange = { newValue ->
                    val filteredValue = newValue.filter { it.isDigit() }
                    height = filteredValue
                    heightError = validateNumber(filteredValue, "Estatura", 50, 250)
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Estatura (cm)", color = Color.Gray) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = heightError.isNotEmpty(),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Create, contentDescription = "Icono de Estatura",tint = Color(0xFFBEA314))
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF2C2B30),
                    unfocusedContainerColor = Color(0xFF2C2B30),
                    disabledContainerColor = Color(0xFF2C2B30),
                    focusedBorderColor = if (heightError.isNotEmpty()) Color.Red else goldColor,
                    unfocusedBorderColor = if (heightError.isNotEmpty()) Color.Red else goldColor,
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
            if (heightError.isNotEmpty()) {
                Text(
                    text = heightError,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        // Sección de Credenciales de Acceso
        Text(text = "Credenciales de Acceso", color = Color(0xFFBEA314), fontSize = 14.sp, textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        // Email

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Email", color = Color.Gray) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = "Icono de Email",tint = Color(0xFFBEA314))
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF2C2B30),
                unfocusedContainerColor = Color(0xFF2C2B30),
                disabledContainerColor = Color(0xFF2C2B30),
                focusedBorderColor = goldColor,
                unfocusedBorderColor = goldColor,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Contraseña con visibilidad toggleable
        var passwordVisible by remember { mutableStateOf(false) }
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Contraseña", color = Color.Gray) },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "Icono de candado",tint = Color(0xFFBEA314))
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF2C2B30),
                unfocusedContainerColor = Color(0xFF2C2B30),
                disabledContainerColor = Color(0xFF2C2B30),
                focusedBorderColor = goldColor,
                unfocusedBorderColor = goldColor,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Botón de Crear Cuenta
        Button(
            onClick = { /* Handle create account click */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFBEA314),
                contentColor = Color.Black
            ),
        ) {
            Text(text = "Crear Cuenta", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

// Componente GenderButton (necesario para el funcionamiento)
@Composable
fun GenderButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val goldColor = Color(0xFFBEA314)

    Button(
        onClick = onClick,
        modifier = Modifier
            .width(100.dp)
            .height(48.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) goldColor else Color(0xFF2C2B30),
            contentColor = if (isSelected) Color.Black else Color.White
        ),
        border = BorderStroke(1.dp, goldColor)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
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