package com.liangguo.translator.app.translate.model


/**
 * @author ldh
 * 时间: 2022/10/26 21:13
 * 邮箱: 2637614077@qq.com
 *
 * 这个数据类包含了一些平台特有性的操作
 *
 *
 *
 */
open class PlatformAction(val android: Android? = null) {

    /**
     * 安卓平台的操作
     * @param onCloseSoftKeyboard 关闭软键盘的操作
     */
    data class Android(val onCloseSoftKeyboard: () -> Unit)

}