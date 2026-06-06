package com.gasstation.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gasstation.ui.components.StationCard
import com.gasstation.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToStation: (String) -> Unit,
    onNavigateToCoupons: () -> Unit,
    onNavigateToProfile: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        containerColor = BackgroundGray,
        topBar = {
            TopAppBar(
                title = { Text("Станції", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onNavigateToCoupons) { Icon(Icons.Default.LocalOffer, contentDescription = "Талони") }
                    IconButton(onClick = onNavigateToProfile) { Icon(Icons.Default.Person, contentDescription = "Профіль") }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CardWhite)
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            OutlinedTextField(
                value = state.search, onValueChange = viewModel::onSearchChange,
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                placeholder = { Text("Пошук станцій…") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true, shape = RoundedCornerShape(8.dp)
            )
            when {
                state.isLoading -> Box(Modifier.fillMaxSize(), Alignment.Center) { CircularProgressIndicator() }
                state.error != null -> Box(Modifier.fillMaxSize(), Alignment.Center) { Text(state.error ?: "", color = ErrorRed) }
                else -> LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.filtered, key = { it.stationId }) { station ->
                        StationCard(station = station, onClick = { onNavigateToStation(station.stationId) })
                    }
                }
            }
        }
    }
}
