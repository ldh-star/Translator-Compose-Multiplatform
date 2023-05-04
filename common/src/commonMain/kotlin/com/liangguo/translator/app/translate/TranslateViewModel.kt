package com.liangguo.translator.app.translate

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.text.AnnotatedString
import com.liangguo.common.currentPlatform
import com.liangguo.common.model.Platform
import com.liangguo.translator.app.settings.model.ApiConfig
import com.liangguo.translator.app.settings.model.ScreenOrientation
import com.liangguo.translator.app.settings.model.Setting
import com.liangguo.translator.app.translate.model.*
import com.liangguo.translator.config.LocalPref
import com.liangguo.translator.logic.network.baidu.BaiduErrorCode
import com.liangguo.translator.logic.network.baidu.BaiduTranslator
import com.liangguo.translator.logic.network.youdao.YouDaoTranslator
import com.liangguo.translator.logic.network.youdao.model.Language
import com.liangguo.translator.logic.network.youdao.model.result.YouDaoErrorCode
import com.liangguo.translator.utils.asType
import com.liangguo.translator.utils.randomArrangement
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


/**
 * @author ldh
 * 时间: 2022/10/16 15:32
 * 邮箱: 2637614077@qq.com
 */
class TranslateViewModel(private val platformAction: PlatformAction = PlatformAction()) :
    CoroutineScope by CoroutineScope(Dispatchers.Default) {

    val inputText = MutableStateFlow("")

    val testInfo = mutableStateListOf<String>()

    val scaffoldState = ScaffoldState(DrawerState(DrawerValue.Closed), SnackbarHostState())

    var clipboardManager: ClipboardManager? = null

    val focusRequester = FocusRequester()

    val engine = MutableStateFlow(LocalPref.engine)

    val theme = MutableStateFlow(LocalPref.theme)

    val exitApp = MutableStateFlow(false)

    val apiConfig = ApiConfig()

    val settings = Setting()

    val shouldBeLandscape = MutableStateFlow(currentPlatform == Platform.Desktop)

    private val _uiState = MutableStateFlow(TranslateUiState.Default)
    private val translateUiState
        get() = _uiState.value
    val uiState: TranslateUiState
        @Composable get() = _uiState.collectAsState().value

    /**
     * 因为涉及到切换翻译引擎，所以相应的语言选择索引也有切换
     * 这里用一个数组来记录
     */
    private val languageIndex = Array(TransEngine.Engines.size) {
        intArrayOf(Language.Default.fromIndex, Language.Default.toIndex)
    }

    fun onAction(action: TranslateScreenUiAction) {
        with(action) {
            when (this) {
                is TranslateScreenUiAction.OnInputChanged -> {
                    inputText.value = newText
                }

                TranslateScreenUiAction.DoTranslate -> {
                    platformAction.android?.onCloseSoftKeyboard?.invoke()
                    checkEnableTranslate {
                        updateUiState {
                            copy(
                                translationState = TranslateState.Loading,
                                loadingState = LoadingState.Translating
                            )
                        }
                        launch {
                            val result = translate()
                            updateUiState {
                                copy(
                                    translationState = result,
                                    loadingState = LoadingState.Nothing,
                                )
                            }
                        }
                    }
                }

                is TranslateScreenUiAction.OnLanguageChanged -> {
                    updateUiState { copy(language = newLang) }
                }

                TranslateScreenUiAction.OnSwapLanguage -> {
                    updateUiState { copy(swapLangButtonState = !swapLangButtonState) }
                    val lang = translateUiState.language
                    onAction(
                        TranslateScreenUiAction.OnLanguageChanged(
                            Language(
                                fromIndex = lang.toIndex,
                                toIndex = lang.fromIndex
                            )
                        )
                    )
                }

                is TranslateScreenUiAction.ChangeMenuVisible -> {
                    if (isSrc) {
                        updateUiState { copy(menu1Open = open, menu2Open = false) }
                    } else {
                        updateUiState { copy(menu2Open = open, menu1Open = false) }
                    }
                }

                TranslateScreenUiAction.ClearAll -> {
                    onAction(TranslateScreenUiAction.OnInputChanged(""))
                    updateUiState { copy(translationState = TranslateState.Nothing) }
                }

                TranslateScreenUiAction.OnCopy -> {
                    clipboardManager?.let { clip ->
                        val str = when (val state = translateUiState.translationState) {
                            is TranslateState.Success.YouDao -> {
                                state.translation.getTranslationString()
                            }

                            is TranslateState.Success.Baidu -> {
                                state.translation.getTranslationString()
                            }

                            else -> null
                        }
                        str?.let {
                            clip.setText(AnnotatedString(it))
                            launch {
                                scaffoldState.snackbarHostState.showSnackbar("已复制 √")
                            }
                        }
                    }
                }

                TranslateScreenUiAction.WeightReduction -> {
                    //降重
                    platformAction.android?.onCloseSoftKeyboard?.invoke()
                    checkEnableTranslate {
                        updateUiState {
                            copy(
                                translationState = TranslateState.Loading,
                                loadingState = LoadingState.ReduceRepetition(0)
                            )
                        }
                        var string: String? = inputText.value
                        launch {
                            //列表里不要有4，因为百度翻译文言文后原意会大大失真
                            val languages = reduceRepLanguages().randomArrangement().toMutableList()
                            languages.add(1)
                            var preLang = 1
                            val engine = this@TranslateViewModel.engine.value
                            languages.forEachIndexed { index, it ->
                                if (string == null) return@forEachIndexed
                                val result =
                                    translate(input = string!!, language = Language(preLang, it), engine = engine)
                                updateUiState {
                                    copy(
                                        translationState = result,
                                        loadingState = LoadingState.ReduceRepetition(((index + 1) * 100) / languages.size)
                                    )
                                }
                                string = result.asType<TranslateState.Success>()?.translation?.getTranslationString()
                                preLang = it

                                //因为百度翻译会限制你的调用频率，所以每次翻译完了之后要等待1秒
                                if (engine == TransEngine.BaiduEngine) delay(1000)
                            }
                            updateUiState { copy(loadingState = LoadingState.Nothing) }
                        }
                    }
                }

                TranslateScreenUiAction.ChangeEngine -> {
                    //切换引擎，先把当前引擎的语言设置保存一下，再加载另一种引擎的语言的设置
                    val current = TransEngine.Engines.indexOf(engine.value)
                    val index = (current + 1) % TransEngine.Engines.size
                    val language = translateUiState.language
                    languageIndex[current].let {
                        it[0] = language.fromIndex
                        it[1] = language.toIndex
                    }
                    val nextArr = languageIndex[index]
                    updateUiState {
                        copy(
                            language = Language(nextArr[0], nextArr[1])
                        )
                    }
                    engine.tryEmit(TransEngine.Engines[index])
                }

                TranslateScreenUiAction.ExitApp -> {
                    exitApp.tryEmit(true)
                }

                is TranslateScreenUiAction.GoScreen -> {
                    updateUiState { copy(uiScreen = screen) }
                }
            }
        }
    }


    private suspend fun translate(
        input: String = inputText.value,
        language: Language = translateUiState.language,
        engine: TransEngine = this.engine.value
    ) =
        withContext(Dispatchers.IO) {
            when (engine) {
                TransEngine.BaiduEngine -> {
                    val result = BaiduTranslator.translate(
                        input,
                        language,
                        apiConfig.baiduAppKey.value,
                        apiConfig.baiduAppSecret.value
                    )
                    result.translation?.let {
                        it.errorCode?.let { errorCode ->
                            TranslateState.Error(
                                errorCode = errorCode,
                                meaning = BaiduErrorCode.getMeaning(errorCode),
                                solution = BaiduErrorCode.getSolution(errorCode)
                            )
                        } ?: TranslateState.Success.Baidu(it)
                    } ?: let {
                        testInfo.add("栈跟踪:${result.exception?.stackTraceToString()}")
                        TranslateState.Exception(result.exception)
                    }
                }

                TransEngine.YouDaoEngine -> {
                    val result = YouDaoTranslator.translate(
                        input,
                        language,
                        apiConfig.youDaoAppKey.value,
                        apiConfig.youDaoAppSecret.value
                    )
                    result.translation?.let {
                        if (it.isError) {
                            TranslateState.Error(
                                errorCode = it.errorCode,
                                meaning = YouDaoErrorCode.getErrorMessage(it.errorCode).orEmpty()
                            )
                        } else {
                            TranslateState.Success.YouDao(it)
                        }
                    } ?: let {
                        testInfo.add("异常栈跟踪:${result.exception?.stackTraceToString()}")
                        TranslateState.Exception(result.exception)
                    }
                }
            }
        }

    /**
     * 更新页面状态
     * 调用时在函数块中用 data class 的 copy函数就行
     */
    fun updateUiState(update: TranslateUiState.() -> TranslateUiState) {
        _uiState.update { update(it) }
    }

    /**
     * 拦截键盘的事件并做出相应的响应
     * 主要是快捷键的逻辑
     */
    @OptIn(ExperimentalComposeUiApi::class)
    fun onPreviewKeyEvent(keyEvent: KeyEvent): Boolean {
        if (keyEvent.type == KeyEventType.KeyDown) {
            val key = keyEvent.key
            if (key == Key.Escape) {
                updateUiState { copy(windowVisible = false) }
            }

            if (keyEvent.isCtrlPressed) {
                if (key == Key.V && translateUiState.uiScreen == UiScreen.Translate) {
                    clipboardManager?.let {
                        if (!focusRequester.captureFocus() || keyEvent.isShiftPressed) {
                            focusRequester.requestFocus()
                            inputText.value = it.getText().toString()
                            onAction(TranslateScreenUiAction.DoTranslate)
                        }
                    }
                }
            }

            if (keyEvent.isAltPressed) {
                when (key) {
                    Key.Backspace -> {
                        onAction(TranslateScreenUiAction.ClearAll)
                        focusRequester.requestFocus()
                        return true
                    }

                    Key.DirectionLeft -> {
                        onAction(TranslateScreenUiAction.ChangeMenuVisible(open = true, true))
                        return true
                    }

                    Key.DirectionRight -> {
                        onAction(TranslateScreenUiAction.ChangeMenuVisible(open = true, false))
                        return true
                    }
                }
            }
        }
        if (keyEvent.key == Key.Enter) {
            // 当按下Enter时，会自动进行翻译
            if (keyEvent.isAltPressed) {
                if (keyEvent.isCtrlPressed) {
                    // 若是Alt + Ctrl + Enter，则会自动聚焦到输入框。
                    focusRequester.requestFocus()
                    return true
                }
                // 若是Alt + Enter，则进行输入换行。
                return false
            } else {
                onAction(TranslateScreenUiAction.DoTranslate)
                return true
            }
        }
        return false
    }

    /**
     * 检查当前是否可以进行翻译
     * 如果可以翻译则会继续执行函数块里的内容
     *
     * 检查的主要项目
     * - 输入是否为空
     * - 翻译的appId等信息是否已经配置
     */
    private fun checkEnableTranslate(ifEnable: () -> Unit) {
        if (inputText.value.isEmpty()) {
            updateUiState { copy(textFieldError = true) }
            launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    "请输入要翻译的文字哦喔~",
                    actionLabel = "知道啦",
                )
            }
        } else if ((!apiConfig.youDaoValid && engine.value == TransEngine.YouDaoEngine) || (!apiConfig.baiduValid && engine.value == TransEngine.BaiduEngine)) {
            updateUiState { copy(translationState = TranslateState.Exception(message = "请先到设置界面配置翻译api")) }
        } else ifEnable()
    }


    /**
     * 根据选择的翻译降重经历几次来生成翻译所用的语言
     */
    private fun reduceRepLanguages(): List<Int> {
        //因为语言数量最低是3，这里就先填充进去了
        var lang = 5
        val langs = mutableListOf(2, 3)
        for (i in 4..settings.reduceRepetitionTimes.value) {
            langs.add(lang++)
        }
        return langs
    }

    init {
        launch {
            engine.collectLatest {
                LocalPref.engine = it
            }
        }
        launch {
            theme.collectLatest {
                LocalPref.theme = it
            }
        }
        launch {
            inputText.collectLatest {
                if (it.isNotEmpty() && translateUiState.textFieldError) {
                    updateUiState { copy(textFieldError = false) }
                }
            }
        }

        launch {
            combine(shouldBeLandscape, settings.screenOrientation) { shouldBeLandscape, orientation ->
                when (orientation) {
                    ScreenOrientation.Landscape -> true
                    ScreenOrientation.Portrait -> false
                    else -> shouldBeLandscape
                }
            }.stateIn(this, SharingStarted.WhileSubscribed(), TranslateUiState.Default.isLandscape).collectLatest {
                updateUiState { copy(isLandscape = it) }
            }
        }

    }


}