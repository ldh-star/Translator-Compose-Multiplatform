// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.liangguo.common.TranslateApp
import com.liangguo.common.model.Res
import com.liangguo.translator.app.translate.TranslateViewModel


fun main() = application {
    val state = rememberWindowState(width = 1000.dp)
    val viewModel = remember { TranslateViewModel() }
    if (viewModel.exitApp.collectAsState().value) {
        exitApplication()
    }
    viewModel.shouldBeLandscape.tryEmit(!state.size.isLandscape)
    //这是一个托盘
    Tray(
        icon = painterResource(Res.drawable.app_icon),
        onAction = {
            viewModel.updateUiState { copy(windowVisible = true) }
        },
        tooltip = "双击打开翻译器",
    ) {
        Item("Exit App", onClick = ::exitApplication)
    }
    Window(
        icon = painterResource(Res.drawable.app_icon_round_corner),
        onCloseRequest = { viewModel.updateUiState { copy(windowVisible = false) } },
        title = "Translator",
        visible = viewModel.uiState.windowVisible,
        state = state,
    ) {
        TranslateApp(viewModel = viewModel)
    }
}

/**
 * 认为 4:3 是区分横竖屏的分界点
 */
private val DpSize.isLandscape: Boolean
    get() = (height / width) > (4f / 3f)
