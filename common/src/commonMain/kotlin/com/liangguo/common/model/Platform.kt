package com.liangguo.common.model


/**
 * @author ldh
 * 时间: 2022/10/26 17:01
 * 邮箱: 2637614077@qq.com
 */
sealed class Platform {

    object Android: Platform()

    object Desktop: Platform()

}
