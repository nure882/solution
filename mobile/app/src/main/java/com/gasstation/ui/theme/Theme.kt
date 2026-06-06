package com.gasstation.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val GasStationColorScheme = lightColorScheme(
    primary              = PrimaryBlue,
    onPrimary            = Color.White,
    primaryContainer     = LightBlue,
    onPrimaryContainer   = PrimaryBlueDark,
    secondary            = MediumText,
    onSecondary          = Color.White,
    secondaryContainer   = SurfaceLight,
    onSecondaryContainer = DarkText,
    tertiary             = GreenSuccess,
    onTertiary           = Color.White,
    background           = BackgroundGray,
    onBackground         = DarkText,
    surface              = CardWhite,
    onSurface            = DarkText,
    surfaceVariant       = SurfaceLight,
    onSurfaceVariant     = MediumText,
    outline              = BorderGray,
    outlineVariant       = BorderLight,
    error                = ErrorRed,
    onError              = Color.White,
    errorContainer       = ErrorBg,
    onErrorContainer     = ErrorRed,
)

@Composable
fun GasStationTheme(content: @Composable () -> Unit) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = CardWhite.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }
    MaterialTheme(
        colorScheme = GasStationColorScheme,
        typography  = Typography,
        content     = content
    )
}
