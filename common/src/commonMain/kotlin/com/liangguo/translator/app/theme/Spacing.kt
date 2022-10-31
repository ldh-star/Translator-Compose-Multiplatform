package com.liangguo.translator.app.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**
 * @author ldh
 * 时间: 2022/10/31 14:56
 * 邮箱: 2637614077@qq.com
 *
 * @param titleLineSpace 一般是一个标题与其接下来的组件的间距
 * @param moduleLineSpace 一般是两个大的模块直接的间距
 */
data class Spacing(
    val titleLineSpace: Dp = 15.dp,
    val moduleLineSpace: Dp = 30.dp,
)

val LocalSpacing = compositionLocalOf { Spacing() }