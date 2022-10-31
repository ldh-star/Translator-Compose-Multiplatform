package com.liangguo.common

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import com.liangguo.common.model.Platform
import java.io.File


@Composable
expect fun PlatformTooltip(
    text: String,
    modifier: Modifier,
    offset: DpOffset,
    content: @Composable () -> Unit
)

/**
 * 带有滚动条的LazyColumn
 * 平台差异性组件，电脑端可以用鼠标拖动进度，安卓端没法
 */
@Composable
expect fun ScrollbarLazyColumn(modifier: Modifier, content: LazyListScope.() -> Unit)

/**
 * 当前平台是什么
 */
expect val currentPlatform: Platform

/**
 * 由于电脑端和安卓端的文件储存位置不同
 * 所以要基于这个基准文件夹来进行本地储存分类
 */
expect val storageDir: File

