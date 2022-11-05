package com.liangguo.translator.app.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import com.liangguo.translator.app.settings.model.Theme


/**
 * @author ldh
 * 时间: 2022/10/27 20:28
 * 邮箱: 2637614077@qq.com
 */

val Material3LightTheme = lightColorScheme(
    primary = Light_Blue_400,
    secondary = Color(0xFFF9DEDC),
    surface = Blue_Surface,
)

val Material3DarkTheme = darkColorScheme(
    primary = Blue_700,
    secondary = Color(0xFFF9DEDC),
)

val MaterialLightTheme = lightColors(
//    primary = Light_Blue_500
)

val MaterialDarkTheme = darkColors(
//    primary = Light_Blue_700
)
