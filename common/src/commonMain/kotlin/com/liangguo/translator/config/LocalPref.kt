package com.liangguo.translator.config

import com.liangguo.common.storageDir
import com.liangguo.translator.app.settings.model.ScreenOrientation
import com.liangguo.translator.app.settings.model.Setting.Companion.ReduceRepetitionTimesDefault
import com.liangguo.translator.app.settings.model.Theme
import com.liangguo.translator.app.translate.model.TransEngine
import org.liangguo.ktpref.KtPref


/**
 * @author ldh
 * 时间: 2022/10/26 22:25
 * 邮箱: 2637614077@qq.com
 */
object LocalPref {

    private val apiConfig by lazy { KtPref("ApiConfig", storageDir) }

    init {
        KtPref.initialize(storageDir)
    }

    var engine: TransEngine
        set(value) {
            KtPref.default.put(TransEngine.className, value.className)
        }
        get() = TransEngine.Engines.find { KtPref.default.getString(TransEngine.className) == it.className }
            ?: TransEngine.Default


    var theme: Theme
        set(value) {
            KtPref.default.put(Theme.className, value.className)
        }
        get() = Theme.Themes.find { KtPref.default.getString(Theme.className) == it.className }
            ?: Theme.Default

    var screenOrientation: ScreenOrientation
        set(value) {
            KtPref.default.put(ScreenOrientation.className, value.className)
        }
        get() = ScreenOrientation.ScreenOrientations.find { KtPref.default.getString(ScreenOrientation.className) == it.className }
            ?: ScreenOrientation.Default

    var youDaoAppKey: String
        set(value) {
            apiConfig.put("YouDaoAppKey", value)
        }
        get() = apiConfig.getString("YouDaoAppKey", "")

    var youDaoAppSecret: String
        set(value) {
            apiConfig.put("YouDaoAppSecret", value)
        }
        get() = apiConfig.getString("YouDaoAppSecret", "")

    var baiduAppKey: String
        set(value) {
            apiConfig.put("BaiduAppKey", value)
        }
        get() = apiConfig.getString("BaiduAppKey", "")

    var baiduAppSecret: String
        set(value) {
            apiConfig.put("BaiduAppSecret", value)
        }
        get() = apiConfig.getString("BaiduAppSecret", "")


    var reduceRepetitionTimes: Int
        set(value) {
            KtPref.default.put("ReduceRepetitionTimes", value)
        }
        get() = KtPref.default.getInt("ReduceRepetitionTimes", ReduceRepetitionTimesDefault)


}

private val Any.className: String
    get() = javaClass.name.let { if (it.contains('.')) it.substringAfterLast(".") else it }
