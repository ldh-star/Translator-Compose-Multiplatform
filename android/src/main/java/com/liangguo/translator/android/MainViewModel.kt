package com.liangguo.translator.android

import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModel
import com.liangguo.easyingcontext.EasyingContext.currentActivity
import com.liangguo.translator.app.translate.TranslateViewModel
import com.liangguo.translator.app.translate.model.PlatformAction


/**
 * @author ldh
 * 时间: 2022/10/24 20:53
 * 邮箱: 2637614077@qq.com
 */
class MainViewModel : ViewModel() {
    val translateViewModel =
        TranslateViewModel(
            platformAction = PlatformAction(
                android = PlatformAction.Android(onCloseSoftKeyboard = {
                    currentActivity?.getSystemService(InputMethodManager::class.java)?.hideSoftInputFromWindow(
                        currentActivity?.currentFocus?.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS
                    )
                })
            )
        )
}