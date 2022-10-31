package com.liangguo.translator.app.settings.model


/**
 * @author ldh
 * 时间: 2022/10/29 21:20
 * 邮箱: 2637614077@qq.com
 */
sealed interface ScreenOrientation {

    val name:String

    companion object {
        val Default = Auto
        val ScreenOrientations = listOf(Auto, Portrait, Landscape)
    }

    object Auto: ScreenOrientation {
        override val name = "自动"
    }

    object Portrait: ScreenOrientation {
        override val name = "竖屏"
    }


    object Landscape: ScreenOrientation {
        override val name = "横屏"
    }


}