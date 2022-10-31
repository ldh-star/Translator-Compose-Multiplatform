package com.liangguo.translator.app.settings.model

import com.liangguo.translator.config.LocalPref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class Setting {

    val reduceRepetitionTimes = MutableStateFlow(LocalPref.reduceRepetitionTimes)

    val screenOrientation = MutableStateFlow(LocalPref.screenOrientation)

    companion object {
        /**
         * 降重默认转换6次
         */
        const val ReduceRepetitionTimesDefault = 6

        /**
         * 降重设置，总共要经过几次翻译的这个范围，规定至少3次，最多10次
         */
        val ReduceRepetitionRange = 3f..10f
    }

    init {
        CoroutineScope(Dispatchers.IO).apply {
            launch {
                reduceRepetitionTimes.collectLatest {
                    LocalPref.reduceRepetitionTimes = it
                }
            }

            launch {
                screenOrientation.collectLatest {
                    LocalPref.screenOrientation = it
                }
            }

        }
    }
}