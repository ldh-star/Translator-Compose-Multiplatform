package com.liangguo.translator.logic.network.youdao.model

import kotlinx.serialization.Serializable


/**
 * @author ldh
 * 时间: 2022/10/16 17:30
 * 邮箱: 2637614077@qq.com
 */
@Serializable
data class TranslateRequestBean(
    @Serializable
    val from: String,

    @Serializable
    val to: String,

    @Serializable
    val signType: String,

    @Serializable
    val curtime: String,

    @Serializable
    val appKey: String,

    @Serializable
    val q: String,

    @Serializable
    val salt: String,

    @Serializable
    val sign: String,

    @Serializable
    val vocabId: String,

    @Serializable
    val voice: String,

    @Serializable
    val ext: String,

    @Serializable
    val strict: String,
)
