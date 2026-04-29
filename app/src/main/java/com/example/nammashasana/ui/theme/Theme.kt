package com.example.nammashasana.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = BrownAccent,
    secondary = StoneGrey,
    tertiary = BeigeDark,
    background = Beige,
    surface = Beige,
    onPrimary = Beige,
    onSecondary = Beige,
    onBackground = BrownDark,
    onSurface = BrownDark
)

@Composable
fun NammaShasanaTheme( // Changed this from NammaShasaneTheme to match your package naming
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = ClassyTypography,
        content = content
    )
}