package com.liangguo.translator.logic.network.baidu.model

import kotlinx.serialization.Serializable

@Serializable
data class BaiduTransResult(
    val dst: String,
    val src: String
)