@file:Suppress("NewApi")

package com.software.todo.presentation.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
@RequiresApi(Build.VERSION_CODES.S)
private fun getDynamicColorScheme(darkTheme: Boolean): ColorScheme {
    val context = LocalContext.current
    return if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
}

@Composable
@RequiresApi(Build.VERSION_CODES.O)
private fun getLightColorScheme(): ColorScheme {
    return lightColorScheme()
}

@Composable
@RequiresApi(Build.VERSION_CODES.O)
private fun getDarkColorScheme(): ColorScheme {
    return darkColorScheme()
}

@Composable
fun Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> getDynamicColorScheme(darkTheme)
        darkTheme -> getDarkColorScheme()
        else -> getLightColorScheme()
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}