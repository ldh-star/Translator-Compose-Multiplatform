package com.liangguo.translator.logic.network.base


/**
 * @author ldh
 * 时间: 2022/10/23 16:11
 * 邮箱: 2637614077@qq.com
 */
open class LanguageCode {

    open val LANGUAGE_ARRAYS = arrayOf<String>()

    open val LANGUAGE_CODE = arrayOf<String>()

    /**
     * 根据代码查询是哪种语言
     *
     * @param code
     * @return
     */
    fun getLanguage(code: String): String? {
        var i = 0
        val n = LANGUAGE_CODE.size
        while (i < n) {
            if (LANGUAGE_CODE[i] == code) {
                return LANGUAGE_ARRAYS[i]
            }
            i++
        }
        return null
    }

    /**
     * 根据语言查询是哪种代码
     *
     * @param language
     * @return
     */
    fun getCode(language: String): String? {
        var i = 0
        val n = LANGUAGE_ARRAYS.size
        while (i < n) {
            if (LANGUAGE_ARRAYS[i] == language) {
                return LANGUAGE_CODE[i]
            }
            i++
        }
        return null
    }

}