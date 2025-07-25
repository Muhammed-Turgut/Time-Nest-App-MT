package com.muhammedturgut.timenestapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColorDark,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    tertiaryContainer = White, // Navigationbarda seçilen Ekranın hangisi olduğunu yazan yazının rengi
    onTertiaryContainer = Black,//Navigationbarda seçilen Ekranın seçilem rengi,
    onPrimary = PageNameDark,// Bu en üste ekran ismini içeren yerin rengi
    onBackground =GolaScreenTopBarTextColorDark,
    surfaceTint =GolasRowColorDark //Bu Golas ekranındaki Rowların rengi
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColorWhite,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    tertiaryContainer = Black, // Navigationbarda seçilen Ekranın hangisi olduğunu yazan yazının rengi
    onTertiaryContainer = White,//Navigationbarda seçilen Ekranın seçilem rengi,
    onPrimary = PageNameWhite,// Bu en üste ekran ismini içeren yerin rengi
    onBackground = GolaScreenTopBarTextColorWhite,
    surfaceTint = GolasRowColorWhite //Bu Golas ekranındaki Rowların rengi

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun TimeNestAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}