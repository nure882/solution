package com.gasstation.ui.screens.coupons

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
import com.gasstation.data.api.CouponDto
import com.gasstation.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CouponsScreen(
    onNavigateBack: () -> Unit,
    viewModel: CouponsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        containerColor = BackgroundGray,
        topBar = {
            TopAppBar(
                title = { Text("Мої талони", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onNavigateBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CardWhite)
            )
        }
    ) { padding ->
        when {
            state.isLoading -> Box(Modifier.fillMaxSize().padding(padding), Alignment.Center) { CircularProgressIndicator() }
            state.error != null -> Box(Modifier.fillMaxSize().padding(padding), Alignment.Center) { Text(state.error ?: "", color = ErrorRed) }
            state.coupons.isEmpty() -> Box(Modifier.fillMaxSize().padding(padding), Alignment.Center) { Text("У вас немає талонів", color = MediumText) }
            else -> LazyColumn(
                modifier = Modifier.padding(padding).fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.coupons, key = { it.couponId }) { coupon -> CouponCard(coupon) }
            }
        }
    }
}

@Composable
private fun CouponCard(coupon: CouponDto) {
    Card(
        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(0.dp), border = BorderStroke(1.dp, BorderGray)
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text("${coupon.gasTypeName} · ${coupon.liters} л", style = MaterialTheme.typography.titleMedium, color = DarkText)
                Text("Діє до: ${coupon.expirationDate?.substring(0, 10) ?: "—"}", style = MaterialTheme.typography.bodySmall, color = MediumText)
            }
            val label = if (coupon.active) "Активний" else "Використано"
            val bg = if (coupon.active) GreenBg else BorderLight
            val fg = if (coupon.active) GreenSuccess else MediumText
            Surface(shape = RoundedCornerShape(6.dp), color = bg, contentColor = fg) {
                Text(label, Modifier.padding(horizontal = 10.dp, vertical = 4.dp), style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}
