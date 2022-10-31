package com.liangguo.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter


/**
 * @author ldh
 * 时间: 2022/10/26 17:53
 * 邮箱: 2637614077@qq.com
 */
@Composable
actual fun painterResource(res: String): Painter = androidx.compose.ui.res.painterResource(res)
