package com.liangguo.translator.logic.network.youdao.model.result

import com.liangguo.translator.app.translate.interfaces.TransResultInterface
import com.liangguo.translator.logic.network.youdao.model.Language
import kotlinx.serialization.Serializable

/**
 * @author ldh
 * 时间: 2022/10/20 14:57
 * 邮箱: 2637614077@qq.com
 *
 * @param returnPhrase 单词校验后的结果	主要校验字母大小写、单词前含符号、中文简繁体
 * @param query 查询的内容
 * @param errorCode 错误码
 * @param l 源语言和目标语言	可能为null 如 en2zh
 * @param tSpeakUrl 翻译结果发音地址。翻译成功一定存在，需要应用绑定语音合成实例才能正常播放，否则返回110错误码
 * @param web 词义	网络释义，该结果不一定存在
 * @param translation 翻译结果	查询正确时，一定存在
 * @param dict 词典deeplink	查询语种为支持语言时，存在
 * @param webdict webdeeplink	查询语种为支持语言时，存在
 * @param basic 词义	基本词典，查词时才有
 * @param isWord 是否是单词
 * @param speakUrl 查询文本的发音地址，翻译成功一定存在，需要应用绑定语音合成实例才能正常播放，否则返回110错误码
 *
 */
@Serializable
data class YouDaoJsonRootBean(
    @Serializable
    val basic: Basic?,
    @Serializable
    val dict: Dict?,
    @Serializable
    val mTerminalDict: Dict?,
    @Serializable
    val errorCode: String,
    @Serializable
    val isWord: Boolean?,
    @Serializable
    val l: String?,
    @Serializable
    val query: String?,
    @Serializable
    val requestId: String,
    @Serializable
    val returnPhrase: List<String>?,
    @Serializable
    val speakUrl: String?,
    @Serializable
    val tSpeakUrl: String?,
    @Serializable
    val translation: List<String>?,
    @Serializable
    val web: List<Web>?,
    @Serializable
    val webdict: Webdict?,
) : TransResultInterface {

    val isError: Boolean
        get() = (errorCode.toIntOrNull() ?: 0) > 100

    /**
     * 对应 List<String> translation
     *
     * @return
    </String> */
    override fun getTranslationString(): String? {
        if (translation.isNullOrEmpty()) {
            return null
        }
        var sb: StringBuilder? = null
        for (s in translation) {
            if (sb == null) {
                sb = StringBuilder()
            } else {
                sb.append(' ')
            }
            sb.append(s)
        }
        return sb.toString()
    }

    /**
     * 将网络释义的信息格式化成字符串返回
     *
     * @return
     */
    fun getWebExplain(): String? {
        if (web.isNullOrEmpty()) {
            return null
        }
        val sb = StringBuilder()
        for (w in web) {
            sb.append(w.string()).append('\n')
        }
        return sb.toString()
    }

    override val language: Language?
        get() {
            if (l == null) return null
            return Language(
                YouDaoLanguageCode.LANGUAGE_CODE.indexOf(
                    l.substring(
                        0,
                        l.indexOf('2')
                    )
                ),
                YouDaoLanguageCode.LANGUAGE_CODE.indexOf(
                    l.substring(
                        l.indexOf('2') + 1
                    )
                )
            )
        }

}

/*

栈跟踪:java.lang.RuntimeException: Unable to create instance of class com.liangguo.translator.logic.network.model.result.YouDaoJsonRootBean. Registering an InstanceCreator or a TypeAdapter for this type, or adding a no-args constructor may fix this problem.
	at com.google.gson.internal.ConstructorConstructor$16.construct(ConstructorConstructor.java:275)
	at com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$Adapter.read(ReflectiveTypeAdapterFactory.java:211)
	at com.google.gson.Gson.fromJson(Gson.java:991)
	at com.google.gson.Gson.fromJson(Gson.java:956)
	at com.google.gson.Gson.fromJson(Gson.java:905)
	at com.google.gson.Gson.fromJson(Gson.java:876)
	at com.liangguo.translator.logic.network.youdao.NetworkRepository$translate$2.invokeSuspend(NetworkRepository.kt:60)
	at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
	at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:106)
	at kotlinx.coroutines.internal.LimitedDispatcher.run(LimitedDispatcher.kt:42)
	at kotlinx.coroutines.scheduling.TaskImpl.run(Tasks.kt:95)
	at kotlinx.coroutines.scheduling.CoroutineScheduler.runSafely(CoroutineScheduler.kt:570)
	at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.executeTask(CoroutineScheduler.kt:750)
	at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.runWorker(CoroutineScheduler.kt:677)
	at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.run(CoroutineScheduler.kt:664)
Caused by: java.lang.UnsupportedOperationException: Cannot allocate class com.liangguo.translator.logic.network.model.result.YouDaoJsonRootBean. Usage of JDK sun.misc.Unsafe is enabled, but it could not be used. Make sure your runtime is configured correctly.
	at com.google.gson.internal.UnsafeAllocator$4.newInstance(UnsafeAllocator.java:104)
	at com.google.gson.internal.ConstructorConstructor$16.construct(ConstructorConstructor.java:272)
	... 14 more
 */