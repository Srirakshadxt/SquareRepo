package com.sriraksha.squarerepo.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = amber50,
    onPrimary = brown700,
    inversePrimary = amber50,
    secondary = green800,
    onSecondary = gray50,
    background = brown700,
    onBackground = gray50,
    surfaceVariant = black,
    surfaceContainer = amber50
)


private val LightColorScheme = lightColorScheme(
    primary = brown700,
    onPrimary = amber50,
    inversePrimary = brown300,
    secondary = green800,
    onSecondary = gray50,
    background = amber50,
    onBackground = black,
    surfaceVariant = white,
    surfaceContainer = brown700
)

@Composable
fun SquareRepoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    val context = LocalContext.current

    if (!view.isInEditMode && context is Activity) {
        SideEffect {
            val window = context.window
            window.statusBarColor = colorScheme.onPrimary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}