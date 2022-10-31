package com.liangguo.translator.logic.network.youdao.model.result

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames


/**
 * @author ldh
 * 时间: 2022/10/20 14:52
 * 邮箱: 2637614077@qq.com
 *
 * @param explains 基本释义
 * @param examTypes 考试类型：比如 初中，高中，CET4，CET6，考研
 * @param phonetic 默认音标，默认是英式音标，英文查词成功，一定存在
 * @param usPhonetic 美式音标，英文查词成功，一定存在
 * @param ukPhonetic 英式音标，英文查词成功，一定存在
 * @param ukSpeech url 英式发音，英文查词成功，一定存在
 * @param usSpeech url 美式发音，英文查词成功，一定存在
 *
 */
@Serializable
data class Basic(
    val explains: List<String>,
    @SerialName("exam_type")
    val examTypes: List<String>?,
    val phonetic: String?,
    @SerialName("us-phonetic")
    val usPhonetic: String?,
    @SerialName("uk-phonetic")
    val ukPhonetic: String?,
    @SerialName("uk-speech")
    val ukSpeech: String?,
    @SerialName("us-speech")
    val usSpeech: String?,
    val wfs: List<WF>?,
) {
    /**
     * 获取基本释义
     * @return 字符串
     */
    fun getExplainsString(): String? {
        val sb = StringBuilder()
        for (s in explains) {
            sb.append(s).append("; ")
        }
        return sb.toString()
    }

    /**
     * 考试类型转换成单独一个字符串，比如：
     *
     */
    val examString: String?
        get() = if (examTypes.isNullOrEmpty()) null else run {
            val sb =StringBuilder()
            examTypes!!.forEach {
                if (sb.isNotEmpty()) sb.append('/')
                sb.append(it)
            }
            sb.toString()
        }

}

@Serializable
@SerialName("wf")
data class WF(val wf: WordForm)