package com.liangguo.translator.app.translate.model


/**
 * @author ldh
 * 时间: 2022/10/23 13:39
 * 邮箱: 2637614077@qq.com
 */
sealed class LoadingState {

    companion object {
        val Default = Nothing
    }

    /**
     * 正在进行翻译
     */
    object Translating : LoadingState()

    /**
     * 正在降重
     * @param progress 进度，百分制表示 0 ~ 100
     */
    class ReduceRepetition(val progress: Int) : LoadingState()

    object Nothing : LoadingState()
}
