package com.liangguo.translator.logic.network.youdao.model

import com.liangguo.translator.logic.network.base.LanguageCode


/**
 * @author ldh
 * 时间: 2022/10/20 22:22
 * 邮箱: 2637614077@qq.com
 */
data class Language(
    val fromIndex: Int,
    val toIndex: Int,
) {
    companion object {
        val Default = Language(0, 0)
    }

    fun fromLanguage(apiType: LanguageCode): String = apiType.LANGUAGE_ARRAYS[fromIndex]

    fun toLanguage(apiType: LanguageCode): String = apiType.LANGUAGE_ARRAYS[toIndex]

    fun getLanguageString(isFrom: Boolean, apiType: LanguageCode) =
        if (isFrom) fromLanguage(apiType) else toLanguage(apiType)

    fun fromCode(apiType: LanguageCode): String = apiType.LANGUAGE_CODE[fromIndex]

    fun toCode(apiType: LanguageCode): String = apiType.LANGUAGE_CODE[toIndex]

}
