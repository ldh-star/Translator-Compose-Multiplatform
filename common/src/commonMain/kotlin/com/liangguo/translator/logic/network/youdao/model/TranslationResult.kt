package com.liangguo.translator.logic.network.youdao.model

import io.ktor.http.*


/**
 * @author ldh
 * 时间: 2022/10/20 22:33
 * 邮箱: 2637614077@qq.com
 */
data class TranslationResult<T>(
    val state: HttpStatusCode? = null,
    val exception: Exception? = null,
    val translation: T? = null,
)
