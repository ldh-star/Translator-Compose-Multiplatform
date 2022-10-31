package com.liangguo.translator.app.settings.model

import androidx.compose.material.Colors
import androidx.compose.material3.ColorScheme
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

    /**
     * Material3的主题配置
     */
    val materialColorScheme: ColorScheme

    val isLight: Boolean

    val isDark: Boolean

    val name: String

    object Dark : Theme {
        override val materialColorScheme = Material3DarkTheme
        override val materialColors = MaterialDarkTheme
        override val isLight = false
        override val isDark = !isLight
        override val name = "亮色模式"
    }

    object Light : Theme {
        override val materialColorScheme = Material3LightTheme
        override val materialColors = MaterialLightTheme
        override val isLight = true
        override val isDark = !isLight
        override val name = "深色模式"
    }

    companion object {
        val Default = Light
        val Themes = listOf(Light, Dark)
    }

}