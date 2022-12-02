package com.liangguo.translator.app.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalLocalization
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.liangguo.common.PlatformTooltip

/**
 * @author ldh
 * 时间: 2022/10/21 16:01
 * 邮箱: 2637614077@qq.com
 */
@Composable
fun Tooltip(
    text: String,
    modifier: Modifier = Modifier,
    offset: DpOffset = DpOffset.Zero,
    content: @Composable () -> Unit
) {
    PlatformTooltip(text, modifier, offset, content)
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SoundChip(text: String) {
    Chip(
        onClick = {},
        colors = ChipDefaults.chipColors(backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Icon(
            modifier = Modifier.size(21.dp),
            imageVector = Icons.Default.VolumeUp,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = text)
        Spacer(modifier = Modifier.width(3.dp))
    }
}

/**
 * 官方的按钮不能自定义大小，我改装成能自定义大小的
 */
@Composable
fun SizeableExtendedFloatingActionButton(
    text: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
    backgroundColor: Color = MaterialTheme.colors.secondary,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    ExtendedFabSize: Dp = 48.dp,
    ExtendedFabIconPadding: Dp = 12.dp,
    ExtendedFabTextPadding: Dp = 20.dp,
    IconAndTextPadding: Dp = 5.dp,
) {
    FloatingActionButton(
        modifier = modifier.sizeIn(
            minWidth = ExtendedFabSize,
            minHeight = ExtendedFabSize
        ),
        onClick = onClick,
        interactionSource = interactionSource,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = elevation
    ) {
        val startPadding = if (icon == null) ExtendedFabTextPadding else ExtendedFabIconPadding
        Row(
            modifier = Modifier.padding(
                start = startPadding,
                end = ExtendedFabTextPadding
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                icon()
                Spacer(Modifier.width(IconAndTextPadding))
            }
            text()
        }
    }
}

/**
 * 图片模块，但是在安卓端和电脑端需要引用不同的资源
 */
@Composable
fun Image(src: String, modifier: Modifier = Modifier) {
    androidx.compose.foundation.Image(
        painter = com.liangguo.common.painterResource(src),
        contentDescription = null,
        modifier = modifier
    )
}
