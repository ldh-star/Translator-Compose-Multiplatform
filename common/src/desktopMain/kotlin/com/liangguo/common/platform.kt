package com.liangguo.common

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.liangguo.common.model.Platform
import java.io.File


@OptIn(ExperimentalFoundationApi::class)
@Composable
actual fun PlatformTooltip(
    text: String,
    modifier: Modifier,
    offset: DpOffset,
    content: @Composable () -> Unit
) {
    TooltipArea(
        tooltip = {
            Surface(
                modifier = Modifier.shadow(4.dp),
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = text,
                    modifier = Modifier.padding(10.dp)
                )
            }
        },
        modifier = modifier,
        delayMillis = 600, // in milliseconds
        tooltipPlacement = TooltipPlacement.CursorPoint(
            alignment = Alignment.BottomEnd,
            offset = offset
        )
    ) {
        content()
    }
}

@Composable
actual fun ScrollbarLazyColumn(modifier: Modifier, content: LazyListScope.() -> Unit) {
    Row(modifier = modifier) {
        val scrollState = rememberLazyListState()
        LazyColumn(modifier = Modifier.weight(1f), state = scrollState, content = content)
        Row(modifier = Modifier.width(20.dp)) {
            Spacer(modifier = Modifier.weight(1f))
            VerticalScrollbar(
                modifier = Modifier.fillMaxHeight(),
                adapter = rememberScrollbarAdapter(scrollState = scrollState)
            )
        }
    }
}

actual val currentPlatform: Platform = Platform.Desktop

actual val storageDir: File = File("files/")


