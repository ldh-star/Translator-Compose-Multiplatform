package com.liangguo.translator.android

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.liangguo.common.TranslateApp

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TranslateApp(viewModel.translateViewModel)
        }
        viewModel.translateViewModel.exitApp.asLiveData().observe(this) {
            if (it) finish()
        }
        viewModel.translateViewModel.shouldBeLandscape.tryEmit(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
    }

}