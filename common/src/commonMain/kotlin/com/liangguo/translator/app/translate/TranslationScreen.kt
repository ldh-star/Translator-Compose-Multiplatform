package com.liangguo.translator.app.translate

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.liangguo.common.ScrollbarLazyColumn
import com.liangguo.translator.app.common.SizeableExtendedFloatingActionButton
import com.liangguo.translator.app.common.SoundChip
import com.liangguo.translator.app.theme.*
import com.liangguo.translator.app.translate.model.TranslateScreenUiAction
import com.liangguo.translator.app.translate.model.TranslateState
import com.liangguo.translator.logic.network.baidu.BaiduLanguageCode
import com.liangguo.translator.logic.network.baidu.model.BaiduJsonRootBean
import com.liangguo.translator.logic.network.base.LanguageCode
import com.liangguo.translator.logic.network.youdao.model.Language
import com.liangguo.translator.logic.network.youdao.model.result.YouDaoJsonRootBean
import com.liangguo.translator.logic.network.youdao.model.result.YouDaoLanguageCode


/**
 * @author ldh
 * 时间: 2022/10/20 14:37
 * 邮箱: 2637614077@qq.com
 */
@Composable
fun TranslationScreen(viewModel: TranslateViewModel) {
    Crossfade(
        modifier = Modifier.fillMaxSize().padding(start = 20.dp),
        targetState = viewModel.uiState.translationState
    ) { state ->
        when (state) {
            is TranslateState.Success -> {
                TranslationResultPenel(
                    viewModel = viewModel,
                    translation = state.translation.getTranslationString().orEmpty(),
                    languageCodeType = if (state.translation is BaiduJsonRootBean) BaiduLanguageCode else YouDaoLanguageCode,
                    language = state.translation.language
                ) {
                    if (state.translation is BaiduJsonRootBean) {

                    } else if (state.translation is YouDaoJsonRootBean) {
                        YouDaoTranslationContent(
                            viewModel = viewModel,
                            translation = state.translation as YouDaoJsonRootBean
                        )
                    }
                }
            }

            is TranslateState.Exception -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    SelectionContainer {
                        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                            item { Spacer(modifier = Modifier.height(LocalSpacing.current.titleLineSpace)) }

                            item { Text(text = if (state.exception == null) "翻译失败" else "程序发生异常：\n${state.exception}") }

                            item { Spacer(modifier = Modifier.height(LocalSpacing.current.titleLineSpace)) }

                            state.message?.let {
                                item { Text(text = it) }
                            }

                            item { Spacer(modifier = Modifier.height(LocalSpacing.current.titleLineSpace)) }
                        }
                    }
                }

            }

            TranslateState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            TranslateState.Nothing -> {

            }

            is TranslateState.Error -> {
                SelectionContainer {
                    Box(modifier = Modifier.fillMaxSize().padding(40.dp), contentAlignment = Alignment.Center) {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            item {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "系统出问题啦！",
                                        fontSize = 19.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.alpha(0.9f),
                                        color = Deep_Orange_A700,
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                }
                            }

                            item { Spacer(modifier = Modifier.height(60.dp)) }

                            item {
                                Text("错误码： ${state.errorCode}", modifier = Modifier.fillMaxWidth(), color = Red_500)
                            }

                            item { Spacer(modifier = Modifier.height(20.dp)) }

                            item {
                                Text(state.meaning, modifier = Modifier.fillMaxWidth())
                            }

                            item { Spacer(modifier = Modifier.height(20.dp)) }

                            state.solution?.let {
                                item { Spacer(modifier = Modifier.height(20.dp)) }

                                item {
                                    Column(modifier = Modifier.fillMaxWidth()) {
                                        Text(
                                            text = "解决办法：",
                                            color = Green_900,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        )
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Text(text = it)
                                    }
                                }

                                item { Spacer(modifier = Modifier.height(20.dp)) }

                            }

                            item { Spacer(modifier = Modifier.height(20.dp)) }

                        }
                    }
                }

            }
        }
    }

}


@Composable
fun TranslationResultPenel(
    viewModel: TranslateViewModel,
    translation: String,
    language: Language? = null,
    languageCodeType: LanguageCode? = null,
    content: (LazyListScope.() -> Unit)? = null
) {
    SelectionContainer {

        ScrollbarLazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }

            item {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = translation,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }

            if (language != null && languageCodeType != null) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${language.fromLanguage(languageCodeType)} → ${
                                language.toLanguage(
                                    languageCodeType
                                )
                            }",
                            fontSize = 14.sp,
                            modifier = Modifier.alpha(0.8f)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        SizeableExtendedFloatingActionButton(
                            modifier = Modifier.padding(start = 25.dp),
                            text = {
                                Text(text = "复制", fontSize = 15.sp)
                            },
                            icon = {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = null
                                )
                            },
                            onClick = {
                                viewModel.onAction(TranslateScreenUiAction.OnCopy)
                            },
                            ExtendedFabSize = 36.dp,
                            ExtendedFabIconPadding = 18.dp,
                            backgroundColor = Light_Blue_100,
                            contentColor = Light_Blue_700,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }

            content?.invoke(this)

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }

        }


    }
}


fun LazyListScope.YouDaoTranslationContent(viewModel: TranslateViewModel, translation: YouDaoJsonRootBean) {
    translation.basic?.let { basic ->
        basic.apply {
            item {
                Row {
                    phonetic?.let {
                        if (ukPhonetic == null || usPhonetic == null) {
                            SoundChip(text = "[ $it ]")
                            Spacer(modifier = Modifier.width(15.dp))
                        }
                    }

                    ukPhonetic?.let {
                        SoundChip(text = "英 [ $it ]")
                        Spacer(modifier = Modifier.width(15.dp))
                    }

                    usPhonetic?.let {
                        SoundChip(text = "美 [ $it ]")
                        Spacer(modifier = Modifier.width(15.dp))
                    }
                }
            }
        }


        item {
            Spacer(modifier = Modifier.height(25.dp))
        }


        items(basic.explains) { explain ->
            Column {
                Text(text = explain, fontSize = 15.sp, modifier = Modifier.alpha(0.9f))
                Spacer(modifier = Modifier.height(15.dp))
            }
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
        }

        basic.examString?.let {
            item {
                Row {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = it, fontSize = 13.sp, modifier = Modifier.alpha(0.7f))
                }
            }
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        items(basic.wfs.orEmpty()) { wf ->
            with(wf.wf) {
                Row(modifier = Modifier.padding(bottom = 10.dp)) {
                    Text(text = name, fontSize = 14.sp, modifier = Modifier.alpha(0.75f))
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = value,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.alpha(0.8f)
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
    }


    translation.getWebExplain()?.let {
        item {
            Spacer(modifier = Modifier.height(10.dp))
        }

        item {
            Text(text = "-网络释义-", fontSize = 14.sp, modifier = Modifier.alpha(0.5f))
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
        }

        translation.web?.forEach { web ->
            item {
                Text(text = web.key, color = Light_Blue_700)
            }
            item {
                Spacer(modifier = Modifier.height(5.dp))
            }

            item {
                Text(text = web.string())
            }

            item {
                Spacer(modifier = Modifier.height(25.dp))
            }

        }

    }
}
