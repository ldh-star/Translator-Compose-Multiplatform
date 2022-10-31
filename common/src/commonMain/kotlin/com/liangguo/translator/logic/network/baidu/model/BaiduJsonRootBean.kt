package com.liangguo.translator.logic.network.baidu.model

import com.liangguo.translator.app.translate.interfaces.TransResultInterface
import com.liangguo.translator.logic.network.baidu.BaiduLanguageCode
import com.liangguo.translator.logic.network.youdao.model.Language
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaiduJsonRootBean(
    val from: String?,
    val to: String?,
    val trans_result: List<BaiduTransResult>?,
    @SerialName("error_code")
    val errorCode: String?,
    @SerialName("error_msg")
    val errorMsg: String?
) : TransResultInterface {

    override fun getTranslationString(): String? {
        if (trans_result.isNullOrEmpty()) return null
        val sb = StringBuilder()
        trans_result.forEach {
            if (sb.isNotEmpty()) sb.append("; ")
            sb.append(it.dst)
        }
        return sb.toString()
    }

    override val language: Language?
        get() = try {
            Language(
                BaiduLanguageCode.LANGUAGE_CODE.indexOf(from),
                BaiduLanguageCode.LANGUAGE_CODE.indexOf(to)
            )
        } catch (e: Exception) {
            null
        }
}