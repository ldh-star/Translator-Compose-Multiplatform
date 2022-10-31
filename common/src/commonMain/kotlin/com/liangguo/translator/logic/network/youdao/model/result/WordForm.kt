package com.liangguo.translator.logic.network.youdao.model.result

import kotlinx.serialization.Serializable


/**
 * @author ldh
 * 时间: 2022/10/22 17:06
 * 邮箱: 2637614077@qq.com
 *
 * 词性
 * 如  "name": "第三人称单数","value": "funs"
 */
@Serializable
data class WordForm(
    val name: String,
    val value: String
)
