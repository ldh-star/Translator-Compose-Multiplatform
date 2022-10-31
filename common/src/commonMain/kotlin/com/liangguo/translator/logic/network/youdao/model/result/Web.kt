package com.liangguo.translator.logic.network.youdao.model.result

import kotlinx.serialization.Serializable


/**
 * @author ldh
 * 时间: 2022/10/20 14:50
 * 邮箱: 2637614077@qq.com
 */
@Serializable
data class Web(val key: String, val value: List<String>) {
    /**
     * 格式化成字符串
     * @return
     */
    fun string(): String {
        val sb: StringBuilder = StringBuilder()
        for (v in value) {
            if (sb.isNotEmpty()) sb.append(' ')
            sb.append(v)
        }
        sb.append(';')
        return sb.toString()
    }
}
