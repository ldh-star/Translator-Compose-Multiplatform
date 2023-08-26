package com.liangguo.translator.app.settings

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.liangguo.translator.app.common.Tooltip
import com.liangguo.translator.app.settings.model.ScreenOrientation
import com.liangguo.translator.app.settings.model.Setting
import com.liangguo.translator.app.theme.Light_Blue_500
import com.liangguo.translator.app.theme.LocalSpacing
import com.liangguo.translator.app.translate.TransEngineButtonContent
import com.liangguo.translator.app.translate.TranslateViewModel
import com.liangguo.translator.app.translate.model.TransEngine
import com.liangguo.translator.app.translate.model.TranslateScreenUiAction
import com.liangguo.translator.config.AppConfig
import com.liangguo.translator.utils.constraintIn


/**
 * @author ldh
 * 时间: 2022/10/28 20:13
 * 邮箱: 2637614077@qq.com
 */

private const val SettingsFraction = 0.7f

@Composable
fun SettingsScreen(viewModel: TranslateViewModel) {
    LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        item { Spacer(modifier = Modifier.height(LocalSpacing.current.moduleLineSpace)) }

        item {
            SettingSummaryText("翻译接口配置 （点击切换翻译引擎）", modifier = Modifier.fillMaxWidth(SettingsFraction))
        }

        item { Spacer(modifier = Modifier.height(LocalSpacing.current.titleLineSpace)) }

        item { EngineSettingBox(viewModel) }

        item { Spacer(modifier = Modifier.height(LocalSpacing.current.moduleLineSpace)) }

        item {
            SettingSummaryText(text = "降重所经历翻译次数", modifier = Modifier.fillMaxWidth(SettingsFraction))
        }

        item { Spacer(modifier = Modifier.height(LocalSpacing.current.titleLineSpace)) }

        item {
            ReduceRepetitionSettingBox(viewModel)
        }

        item { Spacer(modifier = Modifier.height(LocalSpacing.current.moduleLineSpace)) }

        item {
            SettingSummaryText(text = "屏幕布局", modifier = Modifier.fillMaxWidth(SettingsFraction))
        }

        item { Spacer(modifier = Modifier.height(LocalSpacing.current.titleLineSpace)) }

        item {
            ScreenOrientationSettingBox(viewModel)
        }

        item { Spacer(modifier = Modifier.height(LocalSpacing.current.moduleLineSpace)) }

        item { Spacer(modifier = Modifier.height(40.dp)) }

        item {
            Text(
                text = "v ${AppConfig.VERSION}",
                fontSize = 12.sp,
                modifier = Modifier.alpha(0.5f).fillMaxWidth().padding(vertical = 10.dp),
                textAlign = TextAlign.Center
            )
        }

        item { Spacer(modifier = Modifier.height(10.dp)) }
    }
}


@Composable
fun EngineSettingBox(viewModel: TranslateViewModel, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    val isLandscape = viewModel.uiState.isLandscape
    val engine by viewModel.engine.collectAsState()
    val rotation by animateFloatAsState(if (expanded) 180f else 360f)
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        val fraction by animateFloatAsState(
            targetValue = if (!expanded || isLandscape) SettingsFraction else 0.86f,
            animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
        )
        Column(
            modifier = Modifier.fillMaxWidth(fraction).animateContentSize().background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(26.dp)
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(
                    onClick = {
                        viewModel.onAction(TranslateScreenUiAction.ChangeEngine)
                    },
                    modifier = Modifier.weight(1f).height(52.dp),
                ) {
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                        TransEngineButtonContent({ engine })
                    }
                }
                Tooltip(text = "配置翻译接口") {
                    IconButton(onClick = {
                        expanded = !expanded
                    }) {
                        androidx.compose.material.Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            modifier = Modifier.graphicsLayer(rotationZ = rotation)
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically(),
            ) {
                Crossfade(
                    targetState = engine,
                    modifier = Modifier.padding(start = 26.dp, end = 26.dp, bottom = 26.dp, top = 16.dp)
                        .animateContentSize()
                ) {
                    when (it) {
                        TransEngine.YouDaoEngine -> {
                            ApiConfigPanel(
                                url = "https://ai.youdao.com/",
                                text = "有道翻译接口配置",
                                label1 = "有道智云接口的appId",
                                label2 = "有道的app密钥",
                                appId = viewModel.apiConfig.youDaoAppKey.collectAsState().value,
                                appSecret = viewModel.apiConfig.youDaoAppSecret.collectAsState().value,
                                isLandscape = isLandscape,
                            ) { appId, appSecret ->
                                viewModel.apiConfig.youDaoAppKey.tryEmit(appId)
                                viewModel.apiConfig.youDaoAppSecret.tryEmit(appSecret)
                            }
                        }

                        TransEngine.BaiduEngine -> {
                            ApiConfigPanel(
                                url = "http://api.fanyi.baidu.com/",
                                text = "百度翻译接口配置",
                                label1 = "百度接口的appId",
                                label2 = "百度的app密钥",
                                appId = viewModel.apiConfig.baiduAppKey.collectAsState().value,
                                appSecret = viewModel.apiConfig.baiduAppSecret.collectAsState().value,
                                isLandscape = isLandscape,
                            ) { appId, appSecret ->
                                viewModel.apiConfig.baiduAppKey.tryEmit(appId)
                                viewModel.apiConfig.baiduAppSecret.tryEmit(appSecret)
                            }

                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ApiConfigPanel(
    text: String,
    label1: String,
    label2: String,
    url: String? = null,
    appId: String,
    appSecret: String,
    isLandscape: Boolean,
    modifier: Modifier = Modifier,
    onSure: (appId: String, appSecret: String) -> Unit
) {
    var id by remember { mutableStateOf(appId) }
    var secret by remember { mutableStateOf(appSecret) }
    Column(modifier = modifier) {
        Row(modifier = Modifier.height(36.dp)) {
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = androidx.compose.material.MaterialTheme.colors.primary,
                modifier = Modifier.weight(1f),
            )
            AnimatedVisibility(visible = appId != id || secret != appSecret, enter = fadeIn(), exit = fadeOut()) {
                IconButton(
                    onClick = { onSure(id, secret) },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = androidx.compose.material.MaterialTheme.colors.primary)
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row {
            if (isLandscape) {
                Row {
                    OutlinedTextField(
                        value = id,
                        label = { Text(label1) },
                        onValueChange = { id = it },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    OutlinedTextField(
                        value = secret,
                        label = { Text(label2) },
                        onValueChange = { secret = it },
                        modifier = Modifier.weight(1f)
                    )
                }
            } else {
                Column {
                    OutlinedTextField(
                        value = id,
                        label = { Text(label1) },
                        onValueChange = { id = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    OutlinedTextField(
                        value = secret,
                        label = { Text(label2) },
                        onValueChange = { secret = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        if (url != null && (id.isEmpty() || secret.isEmpty())) {
            val uriHandler = LocalUriHandler.current
            val annotatedLinkString = buildAnnotatedString {
                append("去官网注册")
                addStyle(
                    style = SpanStyle(
                        color = Light_Blue_500,
                        textDecoration = TextDecoration.Underline
                    ), start = 0, end = length
                )
                addStringAnnotation(
                    tag = "链接",
                    annotation = url,
                    start = 0,
                    end = length
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp, top = 25.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                androidx.compose.material.Text(text = "没有账号？ ", modifier = Modifier.alpha(0.8f))
                androidx.compose.material.Text(text = annotatedLinkString, modifier = Modifier.clickable {
                    uriHandler.openUri(url)
                })
            }
        }

    }
}

@Composable
fun ReduceRepetitionSettingBox(viewModel: TranslateViewModel) {
    var sliderValue by remember { mutableStateOf(viewModel.settings.reduceRepetitionTimes.value.toFloat()) }
    val range = Setting.ReduceRepetitionRange
    val r = 30.dp
    var btnVisible by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier.fillMaxWidth(SettingsFraction),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .height(2 * r)
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(r)
                ), verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(2 * r), contentAlignment = Alignment.Center) {
                val backgroundFraction = 0.65f
                Box(
                    modifier = Modifier
                        .fillMaxSize(backgroundFraction)
                        .alpha(0.1f)
                        .background(color = androidx.compose.material.MaterialTheme.colors.primary, shape = CircleShape)
                )
                IconButton(
                    onClick = { btnVisible = !btnVisible },
                    modifier = Modifier.fillMaxSize(backgroundFraction)
                ) {
                    androidx.compose.material.Text(text = sliderValue.toInt().toString())
                }
            }
            Spacer(modifier = Modifier.width(2.dp))
            AnimatedVisibility(
                visible = btnVisible,
                enter = fadeIn() + expandHorizontally(),
                exit = fadeOut() + shrinkHorizontally()
            ) {
                androidx.compose.material.IconButton(onClick = {
                    sliderValue = (sliderValue - 1).constraintIn(range)
                }, enabled = sliderValue > range.start) {
                    androidx.compose.material.Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = null)
                }
            }

            androidx.compose.material.Slider(value = sliderValue, onValueChange = {
                sliderValue = it
            }, valueRange = range, onValueChangeFinished = {
                viewModel.settings.reduceRepetitionTimes.tryEmit(sliderValue.toInt())
            }, modifier = Modifier.weight(1f))

            AnimatedVisibility(
                visible = btnVisible,
                enter = fadeIn() + expandHorizontally(),
                exit = fadeOut() + shrinkHorizontally()
            ) {
                androidx.compose.material.IconButton(onClick = {
                    sliderValue = (sliderValue + 1).constraintIn(range)
                }, enabled = sliderValue < range.endInclusive) {
                    androidx.compose.material.Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}


/**
 * 屏幕方向的设置
 */
@Composable
fun ScreenOrientationSettingBox(viewModel: TranslateViewModel) {
    val r = 26.dp
    val screenOrientations = ScreenOrientation.ScreenOrientations
    val screenOrientation by viewModel.settings.screenOrientation.collectAsState()
    Row(modifier = Modifier.height(2 * r).fillMaxWidth(SettingsFraction).alpha(0.8f)) {
        screenOrientations.forEachIndexed { index, current ->
            val first = index == 0
            val last = index == screenOrientations.size - 1
            val selected = current == screenOrientation
            androidx.compose.material.Button(
                onClick = {
                    viewModel.settings.screenOrientation.tryEmit(current)
                },
                modifier = Modifier.fillMaxSize().weight(1f)
                    .graphicsLayer(
                        clip = true, shape = when {
                            first -> RoundedCornerShape(topStartPercent = 50, bottomStartPercent = 50)
                            last -> RoundedCornerShape(topEndPercent = 50, bottomEndPercent = 50)
                            else -> RectangleShape
                        }
                    ),
                colors = androidx.compose.material.ButtonDefaults.buttonColors(backgroundColor = if (selected) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Text(text = current.name)
            }

            if (!last) {
                Spacer(modifier = Modifier.width(3.dp))
            }
        }


    }
}