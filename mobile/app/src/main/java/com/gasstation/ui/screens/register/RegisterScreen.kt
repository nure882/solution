package com.gasstation.ui.screens.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gasstation.ui.theme.*

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(state.isSuccess) { if (state.isSuccess) onRegisterSuccess() }

    Box(modifier = Modifier.fillMaxSize().background(BackgroundGray), contentAlignment = Alignment.Center) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = CardWhite),
            elevation = CardDefaults.cardElevation(0.dp),
            border = BorderStroke(1.dp, BorderGray)
        ) {
            Column(Modifier.padding(24.dp)) {
                Text("Реєстрація", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                Text("Створіть обліковий запис клієнта", style = MaterialTheme.typography.bodyMedium, color = MediumText)
                Spacer(Modifier.height(20.dp))
                AnimatedVisibility(visible = state.error != null) {
                    Surface(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        shape = RoundedCornerShape(8.dp), color = ErrorBg, contentColor = ErrorRed
                    ) { Text(state.error ?: "", Modifier.padding(12.dp), style = MaterialTheme.typography.bodyMedium) }
                }
                OutlinedTextField(value = state.fullName, onValueChange = viewModel::onFullNameChange,
                    label = { Text("Повне ім'я") }, singleLine = true, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp))
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(value = state.email, onValueChange = viewModel::onEmailChange,
                    label = { Text("Email") }, singleLine = true, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp))
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(value = state.password, onValueChange = viewModel::onPasswordChange,
                    label = { Text("Пароль") }, singleLine = true, modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp), visualTransformation = PasswordVisualTransformation())
                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = { viewModel.register() }, enabled = !state.isLoading,
                    modifier = Modifier.fillMaxWidth().height(48.dp), shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                ) {
                    if (state.isLoading) CircularProgressIndicator(Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                    else Text("Зареєструватися", style = MaterialTheme.typography.labelLarge)
                }
                Spacer(Modifier.height(12.dp))
                TextButton(onClick = onNavigateToLogin, modifier = Modifier.fillMaxWidth()) {
                    Text("Вже маєте акаунт? Увійти", color = PrimaryBlue)
                }
            }
        }
    }
}
