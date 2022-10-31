package com.liangguo.translator.app.settings.model

import com.liangguo.translator.config.LocalPref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/**
 * @author ldh
 * 时间: 2022/10/28 22:10
 * 邮箱: 2637614077@qq.com
 */
class ApiConfig {
    val youDaoAppKey = MutableStateFlow(LocalPref.youDaoAppKey)
    val youDaoAppSecret = MutableStateFlow(LocalPref.youDaoAppSecret)
    val baiduAppKey = MutableStateFlow(LocalPref.baiduAppKey)
    val baiduAppSecret = MutableStateFlow(LocalPref.baiduAppSecret)

    val youDaoValid: Boolean
        get() = youDaoAppKey.value.isNotEmpty() && youDaoAppSecret.value.isNotEmpty()

    val baiduValid: Boolean
        get() = baiduAppKey.value.isNotEmpty() && baiduAppSecret.value.isNotEmpty()


    init {
        CoroutineScope(Dispatchers.IO).apply {
            launch {
                youDaoAppKey.collectLatest {
                    LocalPref.youDaoAppKey = it
                }
            }
            launch {
                youDaoAppSecret.collectLatest {
                    LocalPref.youDaoAppSecret = it
                }
            }
            launch {
                baiduAppKey.collectLatest {
                    LocalPref.baiduAppKey = it
                }
            }
            launch {
                baiduAppSecret.collectLatest {
                    LocalPref.baiduAppSecret = it
                }
            }
        }
    }
}