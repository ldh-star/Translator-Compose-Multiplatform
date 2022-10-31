package com.liangguo.translator.app.settings

import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp


/**
 * @author ldh
 * 时间: 2022/10/29 20:43
 * 邮箱: 2637614077@qq.com
 */
@Composable
fun SettingSummaryText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 13.sp,
    color: Color = MaterialTheme.colors.primary
) {
    Text(
        text = text,
        modifier = modifier.alpha(0.9f),
        fontSize = fontSize,
        color = color
    )
}