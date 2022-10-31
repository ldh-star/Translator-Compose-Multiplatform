package com.liangguo.translator.app.translate.model

import com.liangguo.common.currentPlatform
import com.liangguo.common.model.Platform
import com.liangguo.translator.logic.network.youdao.model.Language


/**
 * @author ldh
 * 时间: 2022/10/22 19:14
 * 邮箱: 2637614077@qq.com
 *
 * @param isLandscape 是否是横屏，初始化时手机默认竖屏，电脑默认横屏
 */
data class TranslateUiState(
    val translationState: TranslateState,
    val language: Language,
    val swapLangButtonState: Boolean,
    val menu1Open: Boolean,
    val menu2Open: Boolean,
    val textFieldError: Boolean,
    val windowVisible: Boolean,
    val loadingState: LoadingState,
    val isLandscape: Boolean,
    val uiScreen: UiScreen,
) {
    companion object {
        val Default = TranslateUiState(
            translationState = TranslateState.Nothing,
            language = Language.Default,
            swapLangButtonState = true,
            menu1Open = false,
            menu2Open = false,
            textFieldError = false,
            windowVisible = true,
            loadingState = LoadingState.Default,
            isLandscape = currentPlatform == Platform.Desktop,
            uiScreen = UiScreen.Default,
        )
    }
}