package com.liangguo.common.model


/**
 * @author ldh
 * 时间: 2022/10/26 17:31
 * 邮箱: 2637614077@qq.com
 *
 * 为了解决安卓和desktop平台图片资源的差异性，只能出此下策暂时缓解一下
 */
sealed class Image {

    object YouDao: Image()

    object Baidu: Image()

}
