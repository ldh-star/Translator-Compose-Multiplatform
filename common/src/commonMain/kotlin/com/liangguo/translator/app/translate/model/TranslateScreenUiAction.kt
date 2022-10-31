package com.liangguo.translator.app.translate.model

import com.liangguo.translator.logic.network.youdao.model.Language


/**
 * @author ldh
 * 时间: 2022/10/16 15:36
 * 邮箱: 2637614077@qq.com
 */
sealed class TranslateScreenUiAction {

    class OnInputChanged(val newText: String) : TranslateScreenUiAction()

    object DoTranslate : TranslateScreenUiAction()

    class OnLanguageChanged(val newLang: Language) : TranslateScreenUiAction()

    /**
     * 交换两种语言的转换
     */
    object OnSwapLanguage : TranslateScreenUiAction()

    class ChangeMenuVisible(val open: Boolean, val isSrc: Boolean) : TranslateScreenUiAction()

    object ClearAll : TranslateScreenUiAction()

    object OnCopy: TranslateScreenUiAction()

    /**
     * 降重
     */
    object WeightReduction: TranslateScreenUiAction()

    /**
     * 切换翻译引擎
     */
    object ChangeEngine: TranslateScreenUiAction()


    /**
     * 关闭此应用
     */
    object ExitApp: TranslateScreenUiAction()

    class GoScreen(val screen: UiScreen): TranslateScreenUiAction()

}
