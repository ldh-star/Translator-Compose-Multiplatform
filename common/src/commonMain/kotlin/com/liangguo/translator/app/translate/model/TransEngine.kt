package com.liangguo.translator.app.translate.model

import com.liangguo.common.model.Res


/**
 * @author ldh
 * 时间: 2022/10/23 16:38
 * 邮箱: 2637614077@qq.com
 *
 * 翻译引擎
 */
sealed interface TransEngine {

    val name: String

    val icon: String?

    companion object {
        val Default = YouDaoEngine

        val Engines = listOf(YouDaoEngine, BaiduEngine)
    }

    object YouDaoEngine: TransEngine {
        override val name = "有道翻译"
        override val icon = Res.drawable.icon_youdao
    }

    object BaiduEngine: TransEngine {
        override val name = "百度翻译"
        override val icon = Res.drawable.icon_baidu
    }

}
