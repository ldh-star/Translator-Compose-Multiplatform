package com.liangguo.common

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import com.liangguo.common.model.Platform
import com.liangguo.easyingcontext.EasyingContext.context
import java.io.File

@Composable
actual fun PlatformTooltip(
    text: String,
    modifier: Modifier,
    offset: DpOffset,
    content: @Composable () -> Unit
) {
    content()
}


@Composable
actual fun ScrollbarLazyColumn(modifier: Modifier, content: LazyListScope.() -> Unit) {
    LazyColumn(modifier = modifier, content = content)

}

actual val currentPlatform: Platform = Platform.Android

/**
 * 手机端本地储存的初始目录
 */
actual val storageDir: File =
    context.getExternalFilesDir(null) ?: File("/storage/emulated/0/Android/data/${context.packageName}/files")
