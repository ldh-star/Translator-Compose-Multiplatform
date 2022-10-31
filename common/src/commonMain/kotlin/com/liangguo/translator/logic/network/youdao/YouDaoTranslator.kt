package com.liangguo.translator.logic.network.youdao

import com.liangguo.translator.logic.network.ClientCreator.json
import com.liangguo.translator.logic.network.ClientCreator.ktorClient
import com.liangguo.translator.logic.network.youdao.model.Language
import com.liangguo.translator.logic.network.youdao.model.TranslationResult
import com.liangguo.translator.logic.network.youdao.model.result.YouDaoJsonRootBean
import com.liangguo.translator.logic.network.youdao.model.result.YouDaoLanguageCode.LANGUAGE_CODE
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * @author ldh
 * 时间: 2022/10/16 17:08
 * 邮箱: 2637614077@qq.com
 */
object YouDaoTranslator {

    private const val YOUDAO_URL = "https://openapi.youdao.com/api/"

    /**
     * 这个入口函数进行翻译请求
     */
    suspend fun translate(q: String, language: Language, appKey: String, appSecret: String) =
        withContext(Dispatchers.IO) {
            try {
                val response: HttpResponse = ktorClient().post {
                    url(YOUDAO_URL)
                    setBody(MultiPartFormDataContent(formData {
                        buildBody(
                            query = q,
                            from = LANGUAGE_CODE[language.fromIndex],
                            to = LANGUAGE_CODE[language.toIndex],
                            appKey = appKey,
                            appSecret = appSecret,
                        )
                    }))
                }
                val result = json.decodeFromString<YouDaoJsonRootBean>(response.bodyAsText())
                TranslationResult(response.status, translation = result)
            } catch (e: Exception) {
                TranslationResult(exception = e)
            }

        }

    private fun FormBuilder.buildBody(query: String, from: String, to: String, appKey: String, appSecret: String) {
        val curtime = (System.currentTimeMillis() / 1000).toString()
        val salt = System.currentTimeMillis().toString()
        val signStr: String = appKey + truncate(query) + salt + curtime + appSecret
        val sign = getDigest(signStr).toString()
        append("q", query)
        append("appKey", appKey)
        append("ext", "mp3")
        append("to", to)
        append("from", from)
        append("curtime", curtime)
        append("salt", salt)
        append("sign", sign)
        append("signType", "v3")
        append("strict", "false")
        append("vocabId", "out_id")
        append("voice", "1")
    }

    private fun truncate(q: String?): String? {
        println("输入:[$q]")
        if (q == null) {
            return null
        }
        val len = q.length
        val result = if (len <= 20) q else q.substring(0, 10) + len + q.substring(len - 10, len)
        println("输出:[$result]")
        return result
    }

    /**
     * 生成加密字段
     */
    private fun getDigest(string: String?): String? {
        println("输入[$string]")
        if (string == null) {
            return null
        }
        val hexDigits = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
        val btInput = string.toByteArray(StandardCharsets.UTF_8)
        return try {
            val mdInst = MessageDigest.getInstance("SHA-256")
            mdInst.update(btInput)
            val md = mdInst.digest()
            val j = md.size
            val str = CharArray(j * 2)
            var k = 0
            for (byte0 in md) {
                str[k++] = hexDigits[byte0.toInt() ushr 4 and 0xf]
                str[k++] = hexDigits[byte0.toInt() and 0xf]
            }
            String(str).apply {
                println("输出[$this]")
            }
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            null
        }
    }

}