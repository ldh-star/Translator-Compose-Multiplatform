package com.liangguo.translator.app.translate.model

import com.liangguo.translator.app.translate.interfaces.TransResultInterface
import com.liangguo.translator.logic.network.baidu.model.BaiduJsonRootBean
import com.liangguo.translator.logic.network.youdao.model.result.YouDaoJsonRootBean


/**
 * @author ldh
 * 时间: 2022/10/20 20:46
 * 邮箱: 2637614077@qq.com
 */
sealed class TranslateState {

    sealed class Success(open val translation: TransResultInterface) : TranslateState() {
        class YouDao(override val translation: YouDaoJsonRootBean) : Success(translation)

        class Baidu(override val translation: BaiduJsonRootBean) : Success(translation)
    }


    class Exception(val exception: kotlin.Exception? = null, val message: String? = null) : TranslateState()

    /**
     * 翻译遇到错误
     * @param errorCode 错误码
     * @param meaning 这个错误码什么意思
     * @param solution 解决办法
     */
    class Error(val errorCode: String, val meaning: String, val solution: String? = null):TranslateState()

    object Loading : TranslateState()

    object Nothing : TranslateState()

}
