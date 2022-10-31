package com.liangguo.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CropLandscape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.unit.dp
import com.liangguo.translator.app.theme.Light_Blue_200
import com.liangguo.translator.app.theme.LocalSpacing
import com.liangguo.translator.app.theme.Spacing
import com.liangguo.translator.app.translate.TranslateMainScreen
import com.liangguo.translator.app.translate.TranslateViewModel

@Composable
fun TranslateApp(viewModel: TranslateViewModel = remember { TranslateViewModel() }) {
    val theme by viewModel.theme.collectAsState()
    CompositionLocalProvider(LocalSpacing provides Spacing()) {
        MaterialTheme(colorScheme = theme.materialColorScheme) {
            androidx.compose.material.MaterialTheme(colors = theme.materialColors) {
                Surface(modifier = Modifier.onPreviewKeyEvent(viewModel::onPreviewKeyEvent).fillMaxSize()) {
                    viewModel.clipboardManager = LocalClipboardManager.current
                    TranslateMainScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun DebugControlScreen(viewModel: TranslateViewModel) {
    val theme by viewModel.theme.collectAsState()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        Row {
            IconButton(onClick = {
                viewModel.updateUiState { copy(isLandscape = !isLandscape) }
            }) {
                Icon(imageVector = Icons.Default.CropLandscape, contentDescription = null)
            }
        }
    }
}

@Composable
fun ExceptionPrintScreen(viewModel: TranslateViewModel) {
    SelectionContainer(modifier = Modifier.fillMaxWidth().height(200.dp)) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Button(onClick = {
                    viewModel.testInfo.clear()
                }) {
                    Text("清除并返回")
                }
            }
            itemsIndexed(viewModel.testInfo) { index, info ->
                Box(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        .background(color = if (index % 2 == 0) MaterialTheme.colorScheme.surface else Light_Blue_200)
                ) {
                    Text(text = info)
                }
            }
        }
    }
}
