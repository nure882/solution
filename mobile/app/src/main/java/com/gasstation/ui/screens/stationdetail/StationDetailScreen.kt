package com.gasstation.ui.screens.stationdetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gasstation.data.api.PumpDto
import com.gasstation.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StationDetailScreen(
    stationId: String,
    onNavigateBack: () -> Unit,
    viewModel: StationDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(stationId) { viewModel.load(stationId) }

    Scaffold(
        containerColor = BackgroundGray,
        topBar = {
            TopAppBar(
                title = { Text(state.station?.city ?: "Станція", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CardWhite)
            )
        }
    ) { padding ->
        when {
            state.isLoading -> Box(Modifier.fillMaxSize().padding(padding), Alignment.Center) { CircularProgressIndicator() }
            state.error != null -> Box(Modifier.fillMaxSize().padding(padding), Alignment.Center) { Text(state.error ?: "", color = ErrorRed) }
            else -> LazyColumn(
                modifier = Modifier.padding(padding).fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = CardWhite),
                        elevation = CardDefaults.cardElevation(0.dp), border = BorderStroke(1.dp, BorderGray)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(state.station?.address ?: "", style = MaterialTheme.typography.titleMedium, color = DarkText)
                            Spacer(Modifier.height(8.dp))
                            Text("Режим роботи: ${state.station?.workTime ?: "—"}", style = MaterialTheme.typography.bodyMedium, color = MediumText)
                            if (state.station?.description?.isNotBlank() == true)
                                Text(state.station?.description ?: "", style = MaterialTheme.typography.bodyMedium, color = MediumText)
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    Text("Паливні колонки", style = MaterialTheme.typography.titleSmall, color = DarkText)
                }
                items(state.pumps, key = { it.pumpId }) { pump -> PumpRow(pump) }
                if (state.pumps.isEmpty()) {
                    item { Text("Немає колонок", color = MediumText) }
                }
            }
        }
    }
}

@Composable
private fun PumpRow(pump: PumpDto) {
    Card(
        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(0.dp), border = BorderStroke(1.dp, BorderGray)
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text("Колонка #${pump.number} · ${pump.gasTypeName}", style = MaterialTheme.typography.titleSmall, color = DarkText)
                Text("Залишок: ${pump.gasCount} л", style = MaterialTheme.typography.bodySmall, color = MediumText)
            }
            Text("%.2f".format(pump.cost), style = MaterialTheme.typography.titleMedium, color = PrimaryBlue, fontWeight = FontWeight.Bold)
        }
    }
}
