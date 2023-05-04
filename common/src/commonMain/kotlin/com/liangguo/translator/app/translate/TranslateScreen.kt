package com.liangguo.translator.app.translate

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.outlined.ClearAll
import androidx.compose.material.icons.outlined.GTranslate
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.liangguo.translator.app.common.Image
import com.liangguo.translator.app.common.Tooltip
import com.liangguo.translator.app.settings.SettingsScreen
import com.liangguo.translator.app.theme.CommonSpacePadding
import com.liangguo.translator.app.theme.Light_Blue_100
import com.liangguo.translator.app.theme.Red_50
import com.liangguo.translator.app.translate.model.LoadingState
import com.liangguo.translator.app.translate.model.TransEngine
import com.liangguo.translator.app.translate.model.TranslateScreenUiAction
import com.liangguo.translator.app.translate.model.UiScreen
import com.liangguo.translator.logic.network.baidu.BaiduLanguageCode
import com.liangguo.translator.logic.network.youdao.model.result.YouDaoLanguageCode
import kotlinx.coroutines.launch


/**
 * @author ldh
 * 时间: 2022/10/16 15:27
 * 邮箱: 2637614077@qq.com
 */
@Composable
fun TranslateMainScreen(viewModel: TranslateViewModel) {
    val isLandscape = viewModel.uiState.isLandscape
    val coroutine = rememberCoroutineScope()
    val scaffoldState = viewModel.scaffoldState
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        drawerContent = if (!isLandscape) {
            {
                SideMenuScreen(viewModel, modifier = Modifier.fillMaxSize())
            }
        } else null,
        topBar = {
            if (!isLandscape) {
                SmallTopAppBar(modifier = Modifier.fillMaxWidth(), title = {
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Spacer(modifier = Modifier.width(5.dp))
                        IconButton(onClick = {
                            coroutine.launch {
                                scaffoldState.drawerState.open()
                            }
                        }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(text = "Translator", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(text = "Compose Multiplatform", modifier = Modifier, fontSize = 10.sp)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        if (viewModel.uiState.uiScreen != UiScreen.Settings) {
                            TransEngineTextButton(viewModel)
                        }
                    }
                })
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(color = androidx.compose.material3.MaterialTheme.colorScheme.surface)
        ) {

//            if (viewModel.testInfo.isNotEmpty()) {
//                ExceptionPrintScreen(viewModel)
//            } else {
//                MainContentScreen(viewModel)
//            }

            MainContentScreen(viewModel)

        }
    }
}

@Composable
fun MainContentScreen(viewModel: TranslateViewModel) {
    val content: @Composable (UiScreen) -> Unit = {
        when (it) {
            UiScreen.Translate -> TranslateContentScreen(viewModel)
            UiScreen.Settings -> SettingsScreen(viewModel)
        }
    }
    if (viewModel.uiState.isLandscape) {
        Row(
            modifier = Modifier.fillMaxSize()
                .background(color = androidx.compose.material3.MaterialTheme.colorScheme.surface)
        ) {
            SideMenuScreen(viewModel, modifier = Modifier.width(200.dp))
            Box(
                modifier = Modifier.width(2.dp).fillMaxHeight()
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                androidx.compose.material3.MaterialTheme.colorScheme.secondaryContainer,
                                Color.Transparent
                            )
                        ),
                        alpha = 0.6f
                    )
            )
            Crossfade(targetState = viewModel.uiState.uiScreen, content = content)
        }
    } else {
//        content(viewModel.uiState.uiScreen)
        Crossfade(targetState = viewModel.uiState.uiScreen, content = content)
    }
}

/**
 * 翻译界面主要内容的显示屏
 */
@Composable
fun TranslateContentScreen(viewModel: TranslateViewModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        if (viewModel.uiState.isLandscape) {
            Spacer(modifier = Modifier.height(20.dp))
            ButtonControlPanel(viewModel)
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.weight(1f).padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                InputTextBox(modifier = Modifier.weight(0.5f), viewModel = viewModel)
                Spacer(modifier = Modifier.width(20.dp))
                TranslateResultBox(modifier = Modifier.weight(0.5f), viewModel = viewModel)

            }
        } else {

            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = CommonSpacePadding)) {
                LanguageSelectPanel(viewModel = viewModel, buttonPadding = 6.dp, modifier = Modifier.fillMaxWidth())
            }
            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.weight(1f).padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                InputTextBox(modifier = Modifier.weight(0.3f), viewModel = viewModel)
                Spacer(modifier = Modifier.height(CommonSpacePadding))
                ButtonControlPanel(viewModel)
                Spacer(modifier = Modifier.height(CommonSpacePadding))
                TranslateResultBox(modifier = Modifier.weight(0.7f), viewModel = viewModel)
            }

        }
    }
}


/**
 * 按钮控制面板
 */
@Composable
fun ButtonControlPanel(viewModel: TranslateViewModel) {
    if (viewModel.uiState.isLandscape) {
        //横屏版
        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            item {
                LanguageSelectPanel(viewModel = viewModel)
            }
            item { Spacer(modifier = Modifier.width(CommonSpacePadding)) }
            item {
                TranslateButton(viewModel)
            }
            item { Spacer(modifier = Modifier.width(CommonSpacePadding)) }
            item {
                ReduceRepetitionButton(viewModel)
            }
            item { Spacer(modifier = Modifier.width(CommonSpacePadding)) }
            item {
                TransEngineFloatingButton(viewModel)
            }
            item { Spacer(modifier = Modifier.width(CommonSpacePadding)) }
            item {
                ClearAllButton(viewModel)
            }
            item { Spacer(modifier = Modifier.width(CommonSpacePadding)) }
        }
    } else {
        //竖屏版
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(CommonSpacePadding))
            ReduceRepetitionButton(viewModel)
            Spacer(modifier = Modifier.width(CommonSpacePadding))
            TranslateButton(viewModel)
            Spacer(modifier = Modifier.weight(1f))
            ClearAllButton(viewModel)
            Spacer(modifier = Modifier.width(CommonSpacePadding))
        }
    }
}

@Composable
fun LanguageSelectPanel(modifier: Modifier = Modifier, buttonPadding: Dp = 10.dp, viewModel: TranslateViewModel) {
    if (viewModel.uiState.isLandscape) {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            LanguageDropMenuButton(viewModel = viewModel, isSrcLang = true)
            Spacer(modifier = Modifier.width(buttonPadding))
            ReverseLanguageButton(viewModel)
            Spacer(modifier = Modifier.width(buttonPadding))
            LanguageDropMenuButton(viewModel = viewModel, isSrcLang = false)
        }
    } else {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                LanguageDropMenuButton(viewModel = viewModel, isSrcLang = true)
            }
            Spacer(modifier = Modifier.width(buttonPadding))
            ReverseLanguageButton(viewModel)
            Spacer(modifier = Modifier.width(buttonPadding))
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                LanguageDropMenuButton(viewModel = viewModel, isSrcLang = false)
            }
        }
    }
}


/**
 * 这个模块里装的是翻译结果的屏
 */
@Composable
fun TranslateResultBox(modifier: Modifier = Modifier, viewModel: TranslateViewModel) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(listOf(Color.Gray, Color.Gray)),
                    alpha = 0.10f,
                    shape = RoundedCornerShape(10.dp)
                )
                .alpha(0.5f)
        )
        TranslationScreen(viewModel)
    }
}

/**
 * 输入翻译内容的模块
 */
@Composable
fun InputTextBox(modifier: Modifier = Modifier, viewModel: TranslateViewModel) {
    Row(modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier.fillMaxSize().background(
                brush = Brush.horizontalGradient(listOf(Color.Gray, Color.Gray)),
                alpha = 0.15f,
                shape = RoundedCornerShape(10.dp),
            ).focusRequester(viewModel.focusRequester),
            isError = viewModel.uiState.textFieldError,
            value = viewModel.inputText.collectAsState().value,
            onValueChange = {
                viewModel.onAction(TranslateScreenUiAction.OnInputChanged(it))
            })
    }
}

/**
 * 选择语言的按钮
 * @param isSrcLang 是否是源语言
 */
@Composable
fun LanguageDropMenuButton(modifier: Modifier = Modifier, viewModel: TranslateViewModel, isSrcLang: Boolean) {
    val state = viewModel.uiState
    val language = state.language
    val engine by viewModel.engine.collectAsState()
    val langType = if (engine == TransEngine.YouDaoEngine) YouDaoLanguageCode else BaiduLanguageCode
    Tooltip(
        text = if (isSrcLang) "Alt + ←" else "Alt + →",
        offset = DpOffset(x = 0.dp, y = 14.dp),
        modifier = modifier
    ) {
        Column(modifier = Modifier.animateContentSize()) {
            OutlinedButton(
                onClick = { viewModel.onAction(TranslateScreenUiAction.ChangeMenuVisible(true, isSrc = isSrcLang)) },
                modifier = Modifier.defaultMinSize(minHeight = 50.dp, minWidth = 150.dp)
            ) {
                Text(text = language.getLanguageString(isSrcLang, langType))
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }
            DropdownMenu(
                expanded = if (isSrcLang) state.menu1Open else state.menu2Open,
                onDismissRequest = {
                    viewModel.onAction(
                        TranslateScreenUiAction.ChangeMenuVisible(
                            false,
                            isSrc = isSrcLang
                        )
                    )
                },
            ) {
                langType.LANGUAGE_ARRAYS.forEachIndexed { index, lang ->
                    DropdownMenuItem(onClick = {
                        viewModel.onAction(TranslateScreenUiAction.ChangeMenuVisible(false, isSrc = isSrcLang))
                        viewModel.onAction(
                            TranslateScreenUiAction.OnLanguageChanged(
                                if (isSrcLang) language.copy(fromIndex = index) else language.copy(
                                    toIndex = index
                                )
                            )
                        )
                    }) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = lang, modifier = Modifier.weight(1f))
                            Text(text = langType.LANGUAGE_CODE[index], modifier = Modifier.alpha(0.5f))
                        }
                    }
                }
            }
        }
    }
}

/**
 * 反转源语言和目标语言的按钮
 */
@Composable
fun ReverseLanguageButton(viewModel: TranslateViewModel, modifier: Modifier = Modifier) {
    Tooltip(text = "反转目标语言和源语言", offset = DpOffset(x = 0.dp, y = 14.dp), modifier = modifier) {
        IconButton(onClick = {
            viewModel.onAction(TranslateScreenUiAction.OnSwapLanguage)
        }) {
            val btnState = viewModel.uiState.swapLangButtonState
            val animTrans by animateFloatAsState(
                targetValue = if (btnState) 0f else 180f,
                animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
            )
            Icon(
                modifier = Modifier.graphicsLayer(rotationY = animTrans),
                imageVector = Icons.Default.SwapHoriz,
                contentDescription = null
            )
        }
    }
}

/**
 * 点击进行翻译的按钮
 */
@Composable
fun TranslateButton(viewModel: TranslateViewModel) {
    Box(modifier = Modifier.size(46.dp), contentAlignment = Alignment.Center) {
        val translating = viewModel.uiState.loadingState == LoadingState.Translating
        val animScale by animateFloatAsState(targetValue = if (translating) 0.7f else 1f)
        Tooltip(text = "翻译 (Enter)", modifier = Modifier, offset = DpOffset(x = 0.dp, y = 14.dp)) {
            FloatingActionButton(
                onClick = {
                    viewModel.onAction(TranslateScreenUiAction.DoTranslate)
                },
                backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.scale(animScale)
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Outlined.GTranslate,
                    contentDescription = null
                )
            }
        }
    }
}

/**
 * 点击一键降重的按钮
 */
@Composable
fun ReduceRepetitionButton(viewModel: TranslateViewModel) {
    val state = viewModel.uiState
    Tooltip(text = "将一段文字翻译多次翻译成其他语言，最后再翻译回中文") {
        val loading = state.loadingState is LoadingState.ReduceRepetition
        FloatingActionButton(
            modifier = Modifier.sizeIn(
                minWidth = 48.dp,
                minHeight = 48.dp
            ),
            onClick = {
                viewModel.onAction(TranslateScreenUiAction.WeightReduction)
            },
            backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer
        ) {
            Box(contentAlignment = Alignment.Center) {
                if (loading) {
                    val loadingState = state.loadingState as LoadingState.ReduceRepetition
                    val animProgress by animateFloatAsState(targetValue = loadingState.progress / 100f)
                    androidx.compose.material3.CircularProgressIndicator(
                        modifier = Modifier.size(48.dp).alpha(0.7f),
                        progress = animProgress
                    )
                    Text(text = "${(animProgress * 100).toInt()}%")
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                AnimatedVisibility(
                    visible = !loading,
                    enter = fadeIn() + expandHorizontally(),
                    exit = fadeOut() + shrinkHorizontally()
                ) {
                    Row {
                        Text(modifier = Modifier.padding(horizontal = 18.dp), text = "一键降重")
                    }
                }
            }
        }
    }
}

/**
 * 点击切换翻译引擎的按钮
 * 这是FloatingActionButton类型的
 */
@Composable
fun TransEngineFloatingButton(viewModel: TranslateViewModel) {
    Tooltip(text = "点击切换翻译引擎") {
        val engine by viewModel.engine.collectAsState()
        val color by animateColorAsState(if (engine == TransEngine.YouDaoEngine) Red_50 else Light_Blue_100)
        FloatingActionButton(
            modifier = Modifier.sizeIn(
                minWidth = 48.dp,
                minHeight = 48.dp
            ),
            onClick = {
                viewModel.onAction(TranslateScreenUiAction.ChangeEngine)
            },
            backgroundColor = color,
        ) {
            TransEngineButtonContent({ engine }, Color.Black)
        }
    }
}

/**
 * 点击切换翻译引擎的按钮
 * 这是TextButton类型的
 */
@Composable
fun TransEngineTextButton(
    viewModel: TranslateViewModel,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center
) {
    val engine by viewModel.engine.collectAsState()
    androidx.compose.material3.TextButton(onClick = {
        viewModel.onAction(TranslateScreenUiAction.ChangeEngine)
    }, modifier = modifier) {
        TransEngineButtonContent({ engine })
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TransEngineButtonContent(
    engineProvider: () -> TransEngine,
    fontColor: Color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
) {
    AnimatedContent(targetState = engineProvider(), transitionSpec = {
        fadeIn() + slideInHorizontally() with fadeOut() + slideOutVertically()
    }) { engine ->
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(9.dp))
            engine.icon?.let {
                Image(
                    src = it,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(9.dp))
            Text(text = engine.name, color = fontColor)
            Spacer(modifier = Modifier.width(18.dp))
        }
    }
}

/**
 * 清除所有的按钮
 */
@Composable
fun ClearAllButton(viewModel: TranslateViewModel) {
    AnimatedVisibility(
        visible = viewModel.inputText.collectAsState().value.isNotEmpty(),
        enter = expandHorizontally() + fadeIn(),
        exit = shrinkHorizontally() + fadeOut()
    ) {
        Tooltip(text = "清除  (Alt + Backspace)", offset = DpOffset(x = 0.dp, y = 14.dp)) {
            IconButton(onClick = {
                viewModel.onAction(TranslateScreenUiAction.ClearAll)
            }) {
                Icon(
                    tint = MaterialTheme.colors.primary,
                    imageVector = Icons.Outlined.ClearAll,
                    contentDescription = null
                )
            }
        }
    }
}

