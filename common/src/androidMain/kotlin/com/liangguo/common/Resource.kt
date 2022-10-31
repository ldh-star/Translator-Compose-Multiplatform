package com.liangguo.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.liangguo.translator.common.R


/**
 * @author ldh
 * 时间: 2022/10/26 17:53
 * 邮箱: 2637614077@qq.com
 */
@Composable
actual fun painterResource(res: String): Painter {
    val id = drawableId(res)
    return androidx.compose.ui.res.painterResource(id)
}

@SuppressWarnings
private fun drawableId(res: String): Int {
    val imageName = res.substringAfterLast("/").substringBeforeLast(".")
    val drawableClass = R.drawable::class.java
    val field = drawableClass.getDeclaredField(imageName)
    return field.get(drawableClass) as Int
}