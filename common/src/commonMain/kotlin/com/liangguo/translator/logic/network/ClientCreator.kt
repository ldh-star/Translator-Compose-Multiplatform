package com.liangguo.translator.logic.network

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.logging.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json


/**
 * @author ldh
 * 时间: 2022/10/22 22:50
 * 邮箱: 2637614077@qq.com
 */
object ClientCreator {

    @OptIn(ExperimentalSerializationApi::class)
    val json = Json {
        isLenient = true
        explicitNulls = false

        // 以后就算这个接口出了未知的新属性，那这个json也能继续转义以前已知的而不至于报错。
        ignoreUnknownKeys = true
    }

    fun ktorClient() = HttpClient(OkHttp) {
        expectSuccess = false
        Logging {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println("\n----------------------网络请求日志----------------------")
                    println(message)
                    println("---------------------------结束--------------------------\n\n")
                }
            }
        }
    }

}