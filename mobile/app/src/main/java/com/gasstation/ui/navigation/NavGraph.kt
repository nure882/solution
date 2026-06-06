package com.gasstation.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gasstation.data.local.SessionManager
import com.gasstation.data.repository.AuthRepository
import com.gasstation.ui.screens.coupons.CouponsScreen
import com.gasstation.ui.screens.home.HomeScreen
import com.gasstation.ui.screens.login.LoginScreen
import com.gasstation.ui.screens.profile.ProfileScreen
import com.gasstation.ui.screens.register.RegisterScreen
import com.gasstation.ui.screens.stationdetail.StationDetailScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AuthState {
    object Unknown : AuthState()
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Unknown)
    val authState = _authState.asStateFlow()

    init {
        viewModelScope.launch {
            _authState.value = if (authRepository.isLoggedIn()) AuthState.Authenticated
            else AuthState.Unauthenticated
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _authState.value = AuthState.Unauthenticated
        }
    }
}

object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val STATION_DETAIL = "stationDetail/{stationId}"
    const val COUPONS = "coupons"
    const val PROFILE = "profile"

    fun stationDetail(stationId: String) = "stationDetail/$stationId"
}

@Composable
fun GasStationNavHost(mainViewModel: MainViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val authState by mainViewModel.authState.collectAsState()

    LaunchedEffect(Unit) {
        SessionManager.sessionExpiredFlow.collect {
            navController.navigate(Routes.LOGIN) {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    if (authState == AuthState.Unknown) return

    val startDestination = if (authState == AuthState.Authenticated) Routes.HOME else Routes.LOGIN

    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(280)) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(280)) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(280)) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(280)) }
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = { navController.navigate(Routes.HOME) { popUpTo(Routes.LOGIN) { inclusive = true } } },
                onNavigateToRegister = { navController.navigate(Routes.REGISTER) }
            )
        }
        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate(Routes.HOME) { popUpTo(Routes.LOGIN) { inclusive = true } } },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }
        composable(Routes.HOME) {
            HomeScreen(
                onNavigateToStation = { id -> navController.navigate(Routes.stationDetail(id)) },
                onNavigateToCoupons = { navController.navigate(Routes.COUPONS) },
                onNavigateToProfile = { navController.navigate(Routes.PROFILE) }
            )
        }
        composable(
            route = Routes.STATION_DETAIL,
            arguments = listOf(navArgument("stationId") { type = NavType.StringType })
        ) { backStackEntry ->
            val stationId = backStackEntry.arguments?.getString("stationId") ?: return@composable
            StationDetailScreen(stationId = stationId, onNavigateBack = { navController.popBackStack() })
        }
        composable(Routes.COUPONS) {
            CouponsScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Routes.PROFILE) {
            ProfileScreen(
                onNavigateBack = { navController.popBackStack() },
                onLogout = {
                    mainViewModel.logout()
                    navController.navigate(Routes.LOGIN) { popUpTo(0) { inclusive = true } }
                }
            )
        }
    }
}
