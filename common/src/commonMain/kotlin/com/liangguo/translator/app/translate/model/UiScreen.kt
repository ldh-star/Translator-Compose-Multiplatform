package com.liangguo.translator.app.translate.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Translate
import androidx.compose.ui.graphics.vector.ImageVector


/**
 * @author ldh
 * 时间: 2022/10/28 20:02
 * 邮箱: 2637614077@qq.com
 */
sealed interface UiScreen {

    val name: String

    val icon: ImageVector

    companion object {
        val Default = Translate

        val Screens = listOf(Translate, Settings)
    }

    object Translate : UiScreen {
        override val name = "翻译"
        override val icon = Icons.Default.Translate
    }

    object Settings : UiScreen {
        override val name = "设置"
        override val icon = Icons.Default.Settings
    }

}
