package com.liangguo.translator.app.settings.model

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShieldMoon
import androidx.compose.material.icons.filled.WbAuto
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.liangguo.translator.app.settings.model.Theme.Companion.Themes
import com.liangguo.translator.app.theme.Material3DarkTheme
import com.liangguo.translator.app.theme.Material3LightTheme
import com.liangguo.translator.app.theme.MaterialDarkTheme
import com.liangguo.translator.app.theme.MaterialLightTheme


/**
 * @author ldh
 * 时间: 2022/10/27 21:34
 * 邮箱: 2637614077@qq.com
 */
sealed interface Theme {

    /**
     * material主题的颜色配置
     */
    val materialColors: Colors
        @Composable get

    /**
     * Material3的主题配置
     */
    val materialColorScheme: ColorScheme
        @Composable get

    val isLight: Boolean
        @Composable get

    val isDark: Boolean
        @Composable get

    val name: String

    val iconVector: ImageVector?

    object Dark : Theme {
        override val materialColorScheme
            @Composable get() = Material3DarkTheme
        override val materialColors
            @Composable get() = MaterialDarkTheme
        override val isLight
            @Composable get() = false
        override val isDark
            @Composable get() = !isLight

        override val name = "深色模式"

        override val iconVector: ImageVector = Icons.Default.ShieldMoon
    }

    object Light : Theme {
        override val materialColorScheme
            @Composable get() = Material3LightTheme
        override val materialColors
            @Composable get() = MaterialLightTheme
        override val isLight
            @Composable get() = true
        override val isDark
            @Composable get() = !isLight

        override val name = "亮色模式"

        override val iconVector: ImageVector = Icons.Default.WbSunny
    }

    object Auto : Theme {
        override val materialColorScheme
            @Composable get() = if (isDark) Dark.materialColorScheme else Light.materialColorScheme

        override val materialColors
            @Composable get() = if (isDark) Dark.materialColors else Light.materialColors

        override val isLight
            @Composable get() = !isDark
        override val isDark
            @Composable get() = isSystemInDarkTheme()

        override val name = "自动"

        override val iconVector: ImageVector = Icons.Default.WbAuto
    }

    companion object {
        val Default = Auto
        val Themes = listOf(Auto, Light, Dark)
    }

}

fun Theme.next() = Theme.Themes[(Theme.Themes.indexOf(this) + 1) % Theme.Themes.size]

val Theme.index: Int
    get() = Themes.indexOf(this)
