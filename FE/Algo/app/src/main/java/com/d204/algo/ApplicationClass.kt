package com.d204.algo

import android.app.Application
import android.util.Log
import com.d204.algo.presentation.utils.PresentationPreferencesHelper
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

@HiltAndroidApp
class ApplicationClass : Application() {
    companion object {
        lateinit var preferencesHelper: PresentationPreferencesHelper
        lateinit var kakao_key: String
        lateinit var kakao_scheme_key: String
        var skinOn = false
    }

    override fun onCreate() {
        super.onCreate()
        preferencesHelper = PresentationPreferencesHelper(this)
//        KakaoSdk.init(this, kakao_key)
        KakaoSdk.init(this, BuildConfig.KAKAO_KEY)
    }

    fun readTextFile(file: File) {
        try {
            val br = BufferedReader(FileReader(file))
            kakao_key = br.readLine()
            kakao_scheme_key = br.readLine()
            br.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
