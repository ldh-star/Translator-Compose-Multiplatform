package com.liangguo.translator.app.translate.interfaces

import com.liangguo.translator.logic.network.youdao.model.Language


/**
 * @author ldh
 * 时间: 2022/10/23 17:44
 * 邮箱: 2637614077@qq.com
 */
interface TransResultInterface {

    fun getTranslationString(): String?

    val language: Language?

}