package com.liangguo.translator.logic.network.baidu

import com.liangguo.translator.logic.network.ClientCreator
import com.liangguo.translator.logic.network.ClientCreator.json
import com.liangguo.translator.logic.network.baidu.model.BaiduJsonRootBean
import com.liangguo.translator.logic.network.youdao.model.Language
import com.liangguo.translator.logic.network.youdao.model.TranslationResult
import com.liangguo.translator.utils.MD5
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import java.io.UnsupportedEncodingException
import java.net.URLEncoder


/**
 * @author ldh
 * 时间: 2022/10/23 16:01
 * 邮箱: 2637614077@qq.com
 */
object BaiduTranslator {

    /**
     * 这个入口函数进行翻译请求
     */
    suspend fun translate(q: String, language: Language, appId: String, securityKey: String) =
        withContext(Dispatchers.IO) {
            try {
                val response = ClientCreator.ktorClient().get {
                    val salt = System.currentTimeMillis().toString()
                    val src = appId + q + salt + securityKey
                    val map = mutableMapOf<String, String>()

                    map.apply {
                        put("q", q)
                        put("from", language.fromCode(BaiduLanguageCode))
                        put("to", language.toCode(BaiduLanguageCode))
                        put("appid", appId)
                        put("salt", salt)
                        put("sign", src.MD5)
                    }

                    url(getUrlWithQueryString("http://api.fanyi.baidu.com/api/trans/vip/translate", map))
                }
                val result = json.decodeFromString<BaiduJsonRootBean>(response.bodyAsText())

                return@withContext TranslationResult(state = response.status, translation = result)
            } catch (e: Exception) {
                TranslationResult(exception = e)
            }
        }

    fun getUrlWithQueryString(url: String, params: Map<String, String>?): String {
        if (params == null) {
            return url
        }
        val builder = StringBuilder(url)
        if (url.contains("?")) {
            builder.append("&")
        } else {
            builder.append("?")
        }
        var i = 0
        for (key in params.keys) {
            val value = params[key]
                ?: // 过滤空的key
                continue
            if (i != 0) {
                builder.append('&')
            }
            builder.append(key)
            builder.append('=')
            builder.append(encode(value))
            i++
        }
        return builder.toString()
    }


    /**
     * 对输入的字符串进行URL编码, 即转换为%20这种形式
     *
     * @param input 原文
     * @return URL编码. 如果编码失败, 则返回原文
     */
    fun encode(input: String): String {
        try {
            return URLEncoder.encode(input, "utf-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return input
    }


}