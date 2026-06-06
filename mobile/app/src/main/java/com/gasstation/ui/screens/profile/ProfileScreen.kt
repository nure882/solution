package com.gasstation.ui.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gasstation.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigateBack: () -> Unit,
    onLogout: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        containerColor = BackgroundGray,
        topBar = {
            TopAppBar(
                title = { Text("Профіль", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CardWhite)
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp).fillMaxWidth()) {
            Card(
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = CardWhite),
                elevation = CardDefaults.cardElevation(0.dp), border = BorderStroke(1.dp, BorderGray)
            ) {
                Column(Modifier.padding(20.dp)) {
                    Field("Повне ім'я", state.fullName)
                    Spacer(Modifier.height(12.dp))
                    HorizontalDivider(color = BorderGray)
                    Spacer(Modifier.height(12.dp))
                    Field("Email", state.email)
                    Spacer(Modifier.height(12.dp))
                    HorizontalDivider(color = BorderGray)
                    Spacer(Modifier.height(12.dp))
                    Field("Роль", state.role)
                }
            }
            Spacer(Modifier.height(20.dp))
            OutlinedButton(
                onClick = onLogout, modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(8.dp), border = BorderStroke(1.dp, ErrorRed)
            ) {
                Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null, tint = ErrorRed)
                Spacer(Modifier.width(8.dp))
                Text("Вийти", color = ErrorRed)
            }
        }
    }
}

@Composable
private fun Field(label: String, value: String) {
    Column {
        Text(label, style = MaterialTheme.typography.labelMedium, color = MediumText)
        Text(value.ifBlank { "—" }, style = MaterialTheme.typography.bodyLarge, color = DarkText)
    }
}
